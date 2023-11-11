package com.petra.lib.context.state;

import com.petra.lib.state.ActionState;

import java.util.UUID;

public class ActionStateContext {

    private volatile ActionState currentState;
    private final StateRepo stateRepo;

    private final UUID actionId;

    public ActionStateContext(StateRepo stateRepo, UUID actionId) {
        this.stateRepo = stateRepo;
        this.actionId = actionId;
    }

    public void setCurrentState(ActionState currentState){
        this.currentState = currentState;
        stateRepo.updateState(actionId, currentState);
    }

    public ActionState getState(){
        return currentState;
    }
}
