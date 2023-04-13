package com.petra.lib.manager.block;

import com.petra.lib.manager.state.ExecutionState;

public interface ExecutionStateManager {
    void execute(ExecutionContext executionContext) throws Exception;
    ExecutionState getManagerState();
    void start();

}
