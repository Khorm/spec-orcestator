package com.petra.lib.state;

import com.petra.lib.context.StateError;
import com.petra.lib.context.ExecutionContext;
import com.petra.lib.signal.queue.QueueHandler;

public interface StateManager extends QueueHandler {
    void executedState(ExecutionContext actionContext, State executedState, StateError error);
    void executedState(ExecutionContext actionContext, State executedState);
    void start();
}
