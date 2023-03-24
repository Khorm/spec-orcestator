package com.petra.lib.response;

import com.petra.lib.manager.ExecutionContext;
import com.petra.lib.manager.ExecutionStateManager;
import com.petra.lib.manager.state.ExecutionState;
import com.petra.lib.signal.model.SignalTransferModel;
import com.petra.lib.signal.SignalImpl;
import com.petra.lib.variable.process.ProcessVariable;
import lombok.RequiredArgsConstructor;

import java.util.*;
import java.util.UUID;

@RequiredArgsConstructor
public class ResponseState implements ExecutionStateManager {

    private final SignalImpl signal;

    @Override
    public void execute(ExecutionContext executionContext) throws Exception {
        UUID scenarioId = executionContext.getScenarioId();
        Collection<ProcessVariable> variableList = executionContext.getVariablesList();
        signal.send(scenarioId, variableList, this::sendingException);
    }

    @Override
    public ExecutionState getManagerState() {
        return ExecutionState.EXECUTION_RESPONSE;
    }

    private void sendingException(Exception e, SignalTransferModel signalTransferModel){
        System.out.println(e);
    }
}
