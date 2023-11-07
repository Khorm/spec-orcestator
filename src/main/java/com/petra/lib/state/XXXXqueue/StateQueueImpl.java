package com.petra.lib.state.XXXXqueue;

import com.petra.lib.context.state.ActionState;
import com.petra.lib.state.StateHandler;

import java.util.*;


abstract class StateQueueImpl implements StateQueue {

    final List<ActionState> actionStateList;
    final HashMap<ActionState, StateHandler> stateHandlerHashMap;


    StateQueueImpl(){
        actionStateList = initStateSequence();
        stateHandlerHashMap = initStateHandlers();
    }

    abstract List<ActionState> initStateSequence();
    abstract HashMap<ActionState, StateHandler> initStateHandlers();

    @Override
    public Optional<StateHandler> getNextStateHandler(ActionState actionState) {
        ActionState nextActionState = getNextState(actionState);
        return Optional.ofNullable(stateHandlerHashMap.get(nextActionState));
    }

    @Override
    public StateHandler getInputStateHandler() {
        return stateHandlerHashMap.get(ActionState.INITIALIZING);
    }

    @Override
    public StateHandler getErrorStateHandler() {
        return stateHandlerHashMap.get(ActionState.ERROR);
    }

    private ActionState getNextState(ActionState curActionState) {
        Iterator<ActionState> iter = actionStateList.listIterator();
        while (iter.hasNext()) {
            ActionState actionState = iter.next();
            if (actionState == curActionState) return iter.next();
        }
        return null;
    }
}
