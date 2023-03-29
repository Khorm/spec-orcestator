package com.petra.lib.manager.state;

import com.petra.lib.manager.ExecutionStateManager;

import java.util.Optional;

public interface StateController {
    Optional<ExecutionState> getNextState(ExecutionState prevState);
    ExecutionStateManager getState(ExecutionState state);
}
