package com.petra.lib.signal.source.new_source;

import com.petra.lib.manager.ExecutionContext;
import com.petra.lib.manager.ExecutionHandler;
import com.petra.lib.manager.ExecutionStateManager;
import com.petra.lib.manager.state.ExecutionState;
import com.petra.lib.signal.model.SignalTransferModel;
import com.petra.lib.signal.new_signal.Signal;

import java.util.Set;
import java.util.UUID;

public class SourceSignalRequestManager implements ExecutionStateManager {

    RequestController requestController;
    SourceSignalList sourceSignalList;
    ExecutionHandler executionHandler;


    @Override
    public void execute(ExecutionContext executionContext) {
        try {
            requestController.addNewRequestData(executionContext, executionHandler);
            Set<Long> executedSignalsId = requestController.getExecutedSignals(executionContext.getScenarioId());
            Set<Signal> signalsToExecute = sourceSignalList.getNextAvailableSignals(executedSignalsId);
            signalsToExecute.forEach(signal -> {
                signal.send(executionContext.getScenarioId(), executionContext.getVariablesList(), this::sendingErrorHandler);
            });
        }catch (Exception e){
            requestController.clear(executionContext.getScenarioId());
            executionHandler.executeNext(executionContext, ExecutionState.REQUEST_SOURCE_DATA_ERROR);
        }
    }

    @Override
    public ExecutionState getManagerState() {
        return ExecutionState.REQUEST_SOURCE_DATA;
    }


    private void sendingErrorHandler(Exception E, SignalTransferModel request){
        ExecutionHandler executionHandler = requestController.getExecutionHandler(request.getScenarioId());
        ExecutionContext executionContext = requestController.getExecutionContext(request.getScenarioId());
        requestController.clear(request.getScenarioId());
        executionHandler.executeNext(executionContext, ExecutionState.REQUEST_SOURCE_DATA_ERROR);
    }


    private void receiveMessage(SignalTransferModel signalTransferModel){
        if (!requestController.checkIsScenarioContains(signalTransferModel.getScenarioId())) return;
        UUID scenarioId = signalTransferModel.getScenarioId();
        ExecutionContext context = requestController.getExecutionContext(scenarioId);
        context.setVariables(signalTransferModel.getSignalVariables(), signalTransferModel.getSignalId());
        requestController.addExecutedSourceId(scenarioId, signalTransferModel.getSignalId());
        if (requestController.getReceivedSignalsSize(scenarioId) == sourceSignalList.getSourceSignalsCount()){
            requestController.clear(scenarioId);
            executionHandler.executeNext(context, ExecutionState.REQUEST_SOURCE_DATA);
        }else {
            execute(context);
        }
    }
}
