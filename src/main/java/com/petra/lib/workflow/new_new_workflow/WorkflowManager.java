package com.petra.lib.workflow.new_new_workflow;

import com.petra.lib.XXXXXcontext.DirtyContext;
import com.petra.lib.state.State;
import com.petra.lib.state.StateHandler;

@Deprecated
public class WorkflowManager {

    StateHandler stateHandler;

    @Override
    public void executeState(DirtyContext actionContext, State executedState) {

    }

    @Override
    public void putStateManager(StateHandler stateHandlerManagers) {
        this.stateHandler = stateHandlerManagers;
    }

    @Override
    public void start() {

    }
}
