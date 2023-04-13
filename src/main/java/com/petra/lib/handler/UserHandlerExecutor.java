package com.petra.lib.handler;

import com.petra.lib.manager.block.ExecutionContext;
import com.petra.lib.manager.block.ExecutionHandler;
import com.petra.lib.manager.block.ExecutionStateManager;
import com.petra.lib.manager.state.ExecutionState;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class UserHandlerExecutor implements ExecutionStateManager {

    UserHandler userHandler;
    ExecutionHandler executionHandler;


    @Override
    public void execute(ExecutionContext executionContext) {
        UserContextImpl userContext = new UserContextImpl(executionContext);
        userHandler.execute(userContext);
        executionHandler.executeNext(executionContext, getManagerState());
    }

    @Override
    public ExecutionState getManagerState() {
        return ExecutionState.EXECUTING;
    }

    @Override
    public void start() {
        //do nothin
    }
}
