package com.petra.lib.state;

import java.util.Map;

public class StateManager {

    private final Map<ActionState, StateHandler> stateStateHandlerMap;

    public StateHandler getStateHandler(ActionState actionState){
        return stateStateHandlerMap.get(actionState);
    }
}
