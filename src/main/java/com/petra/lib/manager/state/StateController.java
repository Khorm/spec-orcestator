package com.petra.lib.manager.state;

public interface StateController {
    ExecutionState getNextState(ExecutionState prevState);
}
