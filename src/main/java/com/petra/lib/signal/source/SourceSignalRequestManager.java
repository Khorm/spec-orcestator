package com.petra.lib.signal.source;

import com.petra.lib.manager.block.ExecutionContext;
import com.petra.lib.manager.block.ExecutionHandler;
import com.petra.lib.manager.block.ExecutionStateManager;
import com.petra.lib.manager.block.SourceSignalModel;
import com.petra.lib.manager.state.ExecutionState;
import com.petra.lib.signal.RequestSignal;
import com.petra.lib.signal.SignalObserver;
import com.petra.lib.signal.model.SignalTransferModel;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SourceSignalRequestManager implements ExecutionStateManager, SignalObserver {

    RequestRepo requestRepo;
    SourceSignalList sourceSignalList;
    ExecutionHandler executionHandler;

    /**
     *
     * @param sourceSignalList
     * Список сигналов для соурса
     * @param signals
     * Сами сигналы
     * @param executionHandler
     * Обработчик ответа
     */
    public SourceSignalRequestManager(Collection<SourceSignalModel> sourceSignalList,
                                      List<RequestSignal> signals, ExecutionHandler executionHandler
                                      ){
        this.requestRepo = new LocalRequestRepo();
        this.sourceSignalList = new SourceSignalList(sourceSignalList, signals);
        this.executionHandler = executionHandler;
    }


    @Override
    public void execute(ExecutionContext executionContext) {
        try {
            requestRepo.addNewRequestData(executionContext);
            Set<Long> executedSignalsId = requestRepo.getExecutedSignals(executionContext.getScenarioId());
            Set<RequestSignal> signalsToExecute = sourceSignalList.getNextAvailableSignals(executedSignalsId);
            signalsToExecute.forEach(signal -> {
                signal.send(executionContext.getVariablesList(), executionContext.getScenarioId());
            });
        }catch (Exception e){
            requestRepo.clear(executionContext.getScenarioId());
            executionHandler.executeNext(executionContext, ExecutionState.ERROR);
        }
    }

    @Override
    public ExecutionState getManagerState() {
        return ExecutionState.REQUEST_SOURCE_DATA;
    }

    @Override
    public void start() {
        sourceSignalList.start();
    }


    @Override
    public void executed(SignalTransferModel signalTransferModel) {
        if (!requestRepo.checkIsScenarioContains(signalTransferModel.getScenarioId())) return;

        UUID scenarioId = signalTransferModel.getScenarioId();
        ExecutionContext context = requestRepo.getExecutionContext(scenarioId);
        context.setSignalVariables(signalTransferModel.getSignalVariables());
        requestRepo.addExecutedSourceId(scenarioId, signalTransferModel.getSignalId());

        if (requestRepo.getReceivedSignalsSize(scenarioId) == sourceSignalList.getSourceSignalsCount()){
            requestRepo.clear(scenarioId);
            executionHandler.executeNext(context, ExecutionState.REQUEST_SOURCE_DATA);
        }else {
            execute(context);
        }
    }

    @Override
    public void error(Exception e, SignalTransferModel signalTransferModel) {
        ExecutionContext executionContext = requestRepo.getExecutionContext(signalTransferModel.getScenarioId());
        requestRepo.clear(signalTransferModel.getScenarioId());
        executionHandler.executeNext(executionContext, ExecutionState.ERROR);
    }
}
