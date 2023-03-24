package com.petra.lib.handler;

import com.petra.lib.manager.ExecutionContext;
import com.petra.lib.manager.ExecutionStateManager;
import com.petra.lib.manager.state.ExecutionState;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserHandlerExecutor implements ExecutionStateManager {

    private final UserHandler userHandler;

    @Override
    public void execute(ExecutionContext executionContext) {
        UserContextImpl userContext = new UserContextImpl(executionContext);
        userHandler.execute(userContext);
    }

    @Override
    public ExecutionState getManagerState() {
        return ExecutionState.EXECUTING;
    }
}
