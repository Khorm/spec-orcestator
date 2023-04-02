package com.petra.lib.manager.state;

import com.petra.lib.manager.ExecutionStateManager;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public final class StateControllerImpl implements StateController{

    private final Map<ExecutionState, ExecutionState> stateInheritance = new EnumMap<>(ExecutionState.class);
    /**
     * список стейтменеджеров с применеными стейтами
     */
    private final Map<ExecutionState, ExecutionStateManager> stateList;

    public StateControllerImpl(Map<ExecutionState, ExecutionStateManager> stateList){
        this.stateList = stateList;
        stateInheritance.put(ExecutionState.INITIALIZING, ExecutionState.REQUEST_SOURCE_DATA);
        stateInheritance.put(ExecutionState.REQUEST_SOURCE_DATA, ExecutionState.EXECUTING);
        stateInheritance.put(ExecutionState.EXECUTING, ExecutionState.EXECUTION_REGISTRATION);
        stateInheritance.put(ExecutionState.EXECUTION_REGISTRATION, ExecutionState.EXECUTION_RELEASE);
        stateInheritance.put(ExecutionState.EXECUTION_RELEASE, ExecutionState.EXECUTION_RESPONSE);
    }

    @Override
    public Optional<ExecutionState> getNextState(ExecutionState prevState) {
        return Optional.ofNullable(stateInheritance.get(prevState));
    }

    @Override
    public ExecutionStateManager getState(ExecutionState state) {
        return stateList.get(state);
    }
}
