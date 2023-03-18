package com.petra.lib.manager;

public interface ExecutionHandler {
    void executeNext(ExecutionContext executionContext, ExecutionState executedState, ExecutionBehavior executionBehavior);
}
