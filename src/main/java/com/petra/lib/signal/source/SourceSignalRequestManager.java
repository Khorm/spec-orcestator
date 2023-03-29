package com.petra.lib.signal.source;

import com.petra.lib.manager.ExecutionContext;
import com.petra.lib.manager.ExecutionHandler;
import com.petra.lib.manager.ExecutionStateManager;
import com.petra.lib.manager.factory.SourceSignalModel;
import com.petra.lib.manager.state.ExecutionState;
import com.petra.lib.signal.Signal;
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

    RequestController requestController;
    SourceSignalList sourceSignalList;
    ExecutionHandler executionHandler;

    public SourceSignalRequestManager(Collection<SourceSignalModel> sourceSignalList, List<Signal> signals, ExecutionHandler executionHandler){
        this.requestController = new RequestController();
        this.sourceSignalList = new SourceSignalList(sourceSignalList, signals);
        this.executionHandler = executionHandler;
    }


    @Override
    public void execute(ExecutionContext executionContext) {
        try {
            requestController.addNewRequestData(executionContext, executionHandler);
            Set<Long> executedSignalsId = requestController.getExecutedSignals(executionContext.getScenarioId());
            Set<Signal> signalsToExecute = sourceSignalList.getNextAvailableSignals(executedSignalsId);
            signalsToExecute.forEach(signal -> {
                signal.send(executionContext.getScenarioId(), executionContext.getVariablesList());
            });
        }catch (Exception e){
            requestController.clear(executionContext.getScenarioId());
            executionHandler.executeNext(executionContext, ExecutionState.ERROR);
        }
    }

    @Override
    public ExecutionState getManagerState() {
        return ExecutionState.REQUEST_SOURCE_DATA;
    }


    @Override
    public void executed(SignalTransferModel signalTransferModel) {
        if (!requestController.checkIsScenarioContains(signalTransferModel.getScenarioId())) return;

        UUID scenarioId = signalTransferModel.getScenarioId();
        ExecutionContext context = requestController.getExecutionContext(scenarioId);
        context.setVariables(signalTransferModel.getSignalVariables());
        requestController.addExecutedSourceId(scenarioId, signalTransferModel.getSignalId());

        if (requestController.getReceivedSignalsSize(scenarioId) == sourceSignalList.getSourceSignalsCount()){
            requestController.clear(scenarioId);
            executionHandler.executeNext(context, ExecutionState.REQUEST_SOURCE_DATA);
        }else {
            execute(context);
        }
    }

    @Override
    public void error(Exception e, SignalTransferModel signalTransferModel) {
        ExecutionHandler executionHandler = requestController.getExecutionHandler(signalTransferModel.getScenarioId());
        ExecutionContext executionContext = requestController.getExecutionContext(signalTransferModel.getScenarioId());
        requestController.clear(signalTransferModel.getScenarioId());
        executionHandler.executeNext(executionContext, ExecutionState.ERROR);
    }
}
