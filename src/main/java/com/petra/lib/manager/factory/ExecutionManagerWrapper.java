package com.petra.lib.manager.factory;

import com.petra.lib.manager.ExecutionContext;
import com.petra.lib.manager.ExecutionHandler;
import com.petra.lib.manager.ExecutionManager;
import com.petra.lib.manager.state.ExecutionState;
import lombok.Setter;

class ExecutionManagerWrapper implements ExecutionHandler {

    @Setter
    private ExecutionManager executionManager;

    @Override
    public void executeNext(ExecutionContext executionContext, ExecutionState executedState) {
        executionManager.executeNext(executionContext, executedState);
    }
}
