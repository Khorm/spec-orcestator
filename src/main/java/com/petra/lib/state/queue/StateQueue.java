package com.petra.lib.state.queue;

import com.petra.lib.XXXXXcontext.StateError;
import com.petra.lib.state.State;
import com.petra.lib.state.StateHandler;

import java.util.Optional;

public interface StateQueue {
    Optional<StateHandler> getNextStateHandler(State state);
    StateHandler getInputStateHandler();
    StateHandler getErrorStateHandler();
    StateHandler getErrorHandler(StateError error);
    void start();

}
