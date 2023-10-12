package com.petra.lib.state.queue;

import com.petra.lib.context.StateError;
import com.petra.lib.state.State;
import com.petra.lib.state.StateHandler;
import com.petra.lib.state.StateManager;
import org.apache.kafka.common.metrics.Stat;

import java.util.Optional;

public interface StateQueue {
    Optional<StateHandler> getNextStateHandler(State state);
    StateHandler getInputStateHandler();
    StateHandler getErrorStateHandler();
    StateHandler getErrorHandler(StateError error);
    void start();

}
