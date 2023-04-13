package com.petra.lib.response;

import com.petra.lib.manager.block.ExecutionContext;
import com.petra.lib.manager.block.ExecutionHandler;
import com.petra.lib.manager.block.ExecutionStateManager;
import com.petra.lib.manager.state.ExecutionState;
import com.petra.lib.signal.ResponseSignal;
import com.petra.lib.signal.SignalObserver;
import com.petra.lib.signal.model.SignalTransferModel;
import com.petra.lib.variable.process.ProcessVariable;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.Collection;
import java.util.UUID;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class ResponseState implements ExecutionStateManager, SignalObserver {

    ResponseSignal responseSignal;
    ExecutionHandler executionHandler;

    @Override
    public void execute(ExecutionContext executionContext) throws Exception {
        UUID scenarioId = executionContext.getScenarioId();
        Collection<ProcessVariable> variableList = executionContext.getVariablesList();
        responseSignal.setAnswer(variableList, scenarioId);
        executionHandler.executeNext(executionContext, getManagerState());
    }

    @Override
    public ExecutionState getManagerState() {
        return ExecutionState.EXECUTION_RESPONSE;
    }

    @Override
    public void start() {
    }

    @Override
    public void executed(SignalTransferModel signalTransferModel) {
        //do nothin
    }

    @Override
    public void error(Exception e, SignalTransferModel signalTransferModel) {
        e.printStackTrace();
    }
}
