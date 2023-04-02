package com.petra.lib.release;

import com.petra.lib.manager.ExecutionContext;
import com.petra.lib.manager.ExecutionStateManager;
import com.petra.lib.manager.state.ExecutionState;
import com.petra.lib.signal.Signal;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ReleaseState implements ExecutionStateManager {

    private final Signal inputSignal;

    @Override
    public void execute(ExecutionContext executionContext) throws Exception {
        inputSignal.executeSignal();
    }

    @Override
    public ExecutionState getManagerState() {
        return ExecutionState.EXECUTION_RELEASE;
    }

    @Override
    public void start() {
        //do nothin
    }
}
