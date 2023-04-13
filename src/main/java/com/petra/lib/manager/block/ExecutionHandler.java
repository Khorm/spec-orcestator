package com.petra.lib.manager.block;

import com.petra.lib.manager.state.ExecutionState;

public interface ExecutionHandler {
    void executeNext(ExecutionContext executionContext, ExecutionState executedState);
    void executeState(ExecutionContext executionContext, ExecutionState executedState);
}
