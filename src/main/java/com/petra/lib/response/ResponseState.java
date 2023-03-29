package com.petra.lib.response;

import com.petra.lib.manager.ExecutionContext;
import com.petra.lib.manager.ExecutionHandler;
import com.petra.lib.manager.ExecutionStateManager;
import com.petra.lib.manager.state.ExecutionState;
import com.petra.lib.signal.Signal;
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

    Signal signal;
    ExecutionHandler executionHandler;

    @Override
    public void execute(ExecutionContext executionContext) throws Exception {
        UUID scenarioId = executionContext.getScenarioId();
        Collection<ProcessVariable> variableList = executionContext.getVariablesList();
        signal.send(scenarioId, variableList);
        executionHandler.executeNext(executionContext, getManagerState());
    }

    @Override
    public ExecutionState getManagerState() {
        return ExecutionState.EXECUTION_RESPONSE;
    }

    @Override
    public void executed(SignalTransferModel signalTransferModel) {
        //do nothin
    }

    @Override
    public void error(Exception e, SignalTransferModel signalTransferModel) {
        System.out.println(e);
    }
}
