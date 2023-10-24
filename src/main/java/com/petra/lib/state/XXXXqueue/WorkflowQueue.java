package com.petra.lib.state.XXXXqueue;

import com.petra.lib.state.ActionState;
import com.petra.lib.state.StateHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

class WorkflowQueue extends StateQueueImpl{
    @Override
    List<ActionState> initStateSequence() {
        List<ActionState> actionStates = new ArrayList<>();
        actionStates.add(ActionState.INITIALIZING);
        actionStates.add(ActionState.FILL_CONTEXT_VARIABLES);
        actionStates.add(ActionState.EXECUTING);
        actionStates.add(ActionState.END);
        return actionStates;
    }

    @Override
    HashMap<ActionState, StateHandler> initStateHandlers() {
        return ;
    }
}
