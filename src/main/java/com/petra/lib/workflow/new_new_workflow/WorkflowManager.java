package com.petra.lib.workflow.new_new_workflow;

import com.petra.lib.XXXXXcontext.DirtyContext;
import com.petra.lib.state.ActionState;
import com.petra.lib.state.StateHandler;

@Deprecated
public class WorkflowManager {

    StateHandler stateHandler;

    @Override
    public void executeState(DirtyContext actionContext, ActionState executedActionState) {

    }

    @Override
    public void putStateManager(StateHandler stateHandlerManagers) {
        this.stateHandler = stateHandlerManagers;
    }

    @Override
    public void start() {

    }
}
