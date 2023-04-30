package com.petra.lib.worker.source;

import com.petra.lib.manager.block.JobContext;
import com.petra.lib.manager.block.JobStaticManager;
import com.petra.lib.manager.state.JobStateManager;
import com.petra.lib.manager.block.SourceSignalModel;
import com.petra.lib.manager.state.JobState;
import com.petra.lib.signal.RequestSignal;
import com.petra.lib.signal.SignalRequestListener;
import com.petra.lib.signal.model.SignalTransferModel;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SourceSignalRequestManager implements JobStateManager, SignalRequestListener {

    RequestRepo requestRepo;
    SourceSignalList sourceSignalList;
    JobStaticManager jobStaticManager;

    /**
     *
     * @param sourceSignalList
     * Список сигналов для соурса
     * @param signals
     * Сами сигналы
     * @param jobStaticManager
     * Обработчик ответа
     */
    public SourceSignalRequestManager(Collection<SourceSignalModel> sourceSignalList,
                                      List<RequestSignal> signals, JobStaticManager jobStaticManager
                                      ){
        this.requestRepo = new LocalRequestRepo();
        this.sourceSignalList = new SourceSignalList(sourceSignalList, signals);
        this.jobStaticManager = jobStaticManager;
    }


    @Override
    public void execute(JobContext jobContext) {
        if (sourceSignalList.getSourceSignalsCount() == 0){
            jobStaticManager.executeState(jobContext, JobState.EXECUTING);
            return;
        }
        try {
            requestRepo.addNewRequestData(jobContext);
            Set<Long> executedSignalsId = requestRepo.getExecutedSignals(jobContext.getScenarioId());
            Set<RequestSignal> signalsToExecute = sourceSignalList.getNextAvailableSignals(executedSignalsId);
            signalsToExecute.forEach(signal -> {
                signal.send(jobContext.getVariablesList(), jobContext.getScenarioId());
            });
        }catch (Exception e){
            requestRepo.clear(jobContext.getScenarioId());
            jobStaticManager.executeNext(jobContext, JobState.ERROR);
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

        UUID scenarioId = signalTransferModel.getScenarioId();
        JobContext context = requestRepo.getExecutionContext(scenarioId);
        context.setSignalVariables(signalTransferModel.getSignalVariables());
        requestRepo.addExecutedSourceId(scenarioId, signalTransferModel.getSignalId());

        if (requestRepo.getReceivedSignalsSize(scenarioId) == sourceSignalList.getSourceSignalsCount()){
            requestRepo.clear(scenarioId);
            jobStaticManager.executeNext(context, JobState.REQUEST_SOURCE_DATA);
        }else {
            execute(context);
        }
    }

    @Override
    public void error(Exception e, SignalTransferModel signalTransferModel) {
        JobContext jobContext = requestRepo.getExecutionContext(signalTransferModel.getScenarioId());
        requestRepo.clear(signalTransferModel.getScenarioId());
        jobStaticManager.executeNext(jobContext, JobState.ERROR);
    }
}
