package com.petra.lib.manager.state;

import com.petra.lib.manager.block.ExecutionStateManager;
import com.petra.lib.signal.SignalListener;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

final class StateControllerImpl implements StateController {

    private final Map<ExecutionState, ExecutionState> stateInheritance = new EnumMap<>(ExecutionState.class);
    /**
     * список стейтменеджеров с применеными стейтами
     */

    private final Map<ExecutionState, ExecutionStateManager> stateList = new HashMap<>();

    StateControllerImpl() {
        stateInheritance.put(ExecutionState.INITIALIZING, ExecutionState.REQUEST_SOURCE_DATA);
        stateInheritance.put(ExecutionState.REQUEST_SOURCE_DATA, ExecutionState.EXECUTING);
        stateInheritance.put(ExecutionState.EXECUTING, ExecutionState.EXECUTION_REGISTRATION);
        stateInheritance.put(ExecutionState.EXECUTION_REGISTRATION, ExecutionState.EXECUTION_RESPONSE);
    }

    @Override
    public void registerManager(ExecutionState executionState, ExecutionStateManager manager) {
        stateList.put(executionState, manager);
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
