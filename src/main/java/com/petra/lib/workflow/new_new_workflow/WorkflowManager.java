package com.petra.lib.workflow.new_new_workflow;

import com.petra.lib.context.ExecutionContext;
import com.petra.lib.state.State;
import com.petra.lib.state.StateHandler;
import com.petra.lib.state.StateManager;

@Deprecated
public class WorkflowManager implements StateManager {

    StateHandler stateHandler;

    @Override
    public void executeState(ExecutionContext actionContext, State executedState) {

    }

    @Override
    public void putStateManager(StateHandler stateHandlerManagers) {
        this.stateHandler = stateHandlerManagers;
    }

    @Override
    public void start() {

    }
}
