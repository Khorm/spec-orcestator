package com.petra.lib.state.queue;

import com.petra.lib.state.State;
import com.petra.lib.state.StateHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

class WorkflowQueue extends StateQueueImpl{
    @Override
    List<State> initStateSequence() {
        List<State> states = new ArrayList<>();
        states.add(State.INITIALIZING);
        states.add(State.FILL_CONTEXT_VARIABLES);
        states.add(State.EXECUTING);
        states.add(State.END);
        return states;
    }

    @Override
    HashMap<State, StateHandler> initStateHandlers() {
        return ;
    }
}
