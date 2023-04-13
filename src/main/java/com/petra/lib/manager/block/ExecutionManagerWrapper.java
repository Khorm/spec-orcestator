package com.petra.lib.manager.block;

import com.petra.lib.manager.state.ExecutionState;
import lombok.Setter;

@Deprecated
class ExecutionManagerWrapper implements ExecutionHandler {

    @Setter
    private ExecutionManager executionManager;

    @Override
    public void executeNext(ExecutionContext executionContext, ExecutionState executedState) {
        executionManager.executeNext(executionContext, executedState);
    }

    @Override
    public void executeState(ExecutionContext executionContext, ExecutionState executedState) {
        executionManager.executeState(executionContext, executedState);
    }
}
