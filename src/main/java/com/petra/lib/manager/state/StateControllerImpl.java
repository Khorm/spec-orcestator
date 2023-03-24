package com.petra.lib.manager.state;

import java.util.HashMap;
import java.util.Map;

public final class StateControllerImpl implements StateController{

    private final Map<ExecutionState, ExecutionState> stateInheritance = new HashMap<>();

    private StateControllerImpl(){
        stateInheritance.put(ExecutionState.INITIALIZING, ExecutionState.REQUEST_SOURCE_DATA);
        stateInheritance.put(ExecutionState.REQUEST_SOURCE_DATA, ExecutionState.EXECUTING);
        stateInheritance.put(ExecutionState.EXECUTING, ExecutionState.EXECUTION_REGISTRATION);
        stateInheritance.put(ExecutionState.EXECUTION_REGISTRATION, ExecutionState.EXECUTION_RESPONSE);
    }

    @Override
    public ExecutionState getNextState(ExecutionState prevState) {
        return stateInheritance.get(prevState);
    }
}
