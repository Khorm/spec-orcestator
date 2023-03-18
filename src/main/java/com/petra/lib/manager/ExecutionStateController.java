package com.petra.lib.manager;

public interface ExecutionState {
    void execute(ExecutionContext executionContext, ExecutionHandler executionHandler);
}
