package com.petra.lib.state.XXXXqueue;

import com.petra.lib.XXXXXcontext.StateError;
import com.petra.lib.context.state.ActionState;
import com.petra.lib.state.StateHandler;

import java.util.Optional;

public interface StateQueue {
    Optional<StateHandler> getNextStateHandler(ActionState actionState);
    StateHandler getInputStateHandler();
    StateHandler getErrorStateHandler();
    StateHandler getErrorHandler(StateError error);
    void start();

}
