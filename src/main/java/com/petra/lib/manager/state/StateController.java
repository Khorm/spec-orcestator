package com.petra.lib.manager.state;

import com.petra.lib.manager.block.ExecutionStateManager;
import com.petra.lib.signal.SignalListener;

import java.util.Optional;

public interface StateController {

    Optional<ExecutionState> getNextState(ExecutionState prevState);
    ExecutionStateManager getState(ExecutionState state);
    void registerManager(ExecutionState executionState, ExecutionStateManager manager);

    static StateController createStateController() {
        return new StateControllerImpl();
    }
}
