package com.petra.lib.manager;

import com.petra.lib.manager.state.ExecutionState;

public interface ExecutionStateManager {
    void execute(ExecutionContext executionContext) throws Exception;
    ExecutionState getManagerState();

}
