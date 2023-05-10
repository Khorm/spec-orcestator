package com.petra.lib.worker.source;

import com.petra.lib.manager.block.JobContext;
import com.petra.lib.manager.block.JobStaticManager;
import com.petra.lib.manager.block.ProcessVariableDto;
import com.petra.lib.manager.state.JobStateManager;
import com.petra.lib.manager.block.SourceSignalModel;
import com.petra.lib.manager.state.JobState;
import com.petra.lib.signal.RequestSignal;
import com.petra.lib.signal.SignalRequestListener;
import com.petra.lib.signal.model.SignalTransferModel;
import com.petra.lib.variable.mapper.VariableMapper;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Log4j2
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SourceSignalRequestManager implements JobStateManager, SignalRequestListener {

    RequestRepo requestRepo;
    SourceSignalList sourceSignalList;
    JobStaticManager jobStaticManager;
    VariableMapper variableMapper;
    String blockName;

    /**
     * @param sourceSignalList
     * Список сигналов для соурса
     * @param signals
     * Сами сигналы
     * @param jobStaticManager
     * @param variableMapper
     * @param blockName
     */
    public SourceSignalRequestManager(Collection<SourceSignalModel> sourceSignalList,
                                      List<RequestSignal> signals, JobStaticManager jobStaticManager,
                                      VariableMapper variableMapper, String blockName){
        this.variableMapper = variableMapper;
        this.blockName = blockName;
        this.requestRepo = new LocalRequestRepo();
        this.sourceSignalList = new SourceSignalList(sourceSignalList, signals);
        this.jobStaticManager = jobStaticManager;
    }


    @Override
    public void execute(JobContext jobContext) {
        log.debug("START SourceSignalRequestManager {} {}", blockName, jobContext.toString());
        if (sourceSignalList.getSourceSignalsCount() == 0){
            jobStaticManager.executeState(jobContext, JobState.EXECUTING);
            return;
        }
        try {
            requestRepo.addNewRequestData(jobContext);
            Set<Long> executedSignalsId = requestRepo.getExecutedSignals(jobContext.getScenarioId());
            Set<RequestSignal> signalsToExecute = sourceSignalList.getNextAvailableSignals(executedSignalsId);
            signalsToExecute.forEach(signal -> {
                log.debug("{} SEND to {} {}",blockName, signal.getId(), jobContext.toString());
                signal.send(jobContext.getProcessVariables(), jobContext.getScenarioId());
            });
        }catch (Exception e){
            e.printStackTrace();
            log.error(blockName, e);
            requestRepo.clear(jobContext.getScenarioId());
            jobStaticManager.executeState(jobContext, JobState.ERROR);
        }
    }

    @Override
    public JobState getManagerState() {
        return JobState.REQUEST_SOURCE_DATA;
    }

    @Override
    public void start() {
        sourceSignalList.start();
    }


    @Override
    public void executed(SignalTransferModel signalTransferModel) {
        if (!requestRepo.checkIsScenarioContains(signalTransferModel.getScenarioId())) return;
        log.debug("{} GET ANSWER {}", blockName, signalTransferModel.toString());
        UUID scenarioId = signalTransferModel.getScenarioId();
        JobContext context = requestRepo.getExecutionContext(scenarioId);
        Collection<ProcessVariableDto> mappedSourceVariables = variableMapper.map(signalTransferModel.getSignalVariables());
        mappedSourceVariables.forEach(context::setVariable);
        requestRepo.addExecutedSourceId(scenarioId, signalTransferModel.getSignalId());

        System.out.println(context);
        if (requestRepo.getReceivedSignalsSize(scenarioId) == sourceSignalList.getSourceSignalsCount()){
            requestRepo.clear(scenarioId);
            jobStaticManager.executeState(context, JobState.EXECUTING);
        }else {
            execute(context);
        }
    }

    @Override
    public void error(Exception e, SignalTransferModel signalTransferModel) {
        log.error(blockName, e);
        JobContext jobContext = requestRepo.getExecutionContext(signalTransferModel.getScenarioId());
        requestRepo.clear(signalTransferModel.getScenarioId());
        jobStaticManager.executeState(jobContext, JobState.ERROR);
    }
}
