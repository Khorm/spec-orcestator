package com.petra.lib.state.queue;

import com.petra.lib.state.State;
import com.petra.lib.state.StateHandler;

import java.util.*;


abstract class StateQueueImpl implements StateQueue {

    final List<State> stateList;
    final HashMap<State, StateHandler> stateHandlerHashMap;


    StateQueueImpl(){
        stateList = initStateSequence();
        stateHandlerHashMap = initStateHandlers();
    }

    abstract List<State> initStateSequence();
    abstract HashMap<State, StateHandler> initStateHandlers();

    @Override
    public Optional<StateHandler> getNextStateHandler(State state) {
        State nextState = getNextState(state);
        return Optional.ofNullable(stateHandlerHashMap.get(nextState));
    }

    @Override
    public StateHandler getInputStateHandler() {
        return stateHandlerHashMap.get(State.INITIALIZING);
    }

    @Override
    public StateHandler getErrorStateHandler() {
        return stateHandlerHashMap.get(State.ERROR);
    }

    private State getNextState(State curState) {
        Iterator<State> iter = stateList.listIterator();
        while (iter.hasNext()) {
            State state = iter.next();
            if (state == curState) return iter.next();
        }
        return null;
    }
}
