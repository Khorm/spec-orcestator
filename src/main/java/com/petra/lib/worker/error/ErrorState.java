package com.petra.lib.worker.error;

import com.petra.lib.manager.block.JobContext;
import com.petra.lib.manager.state.JobStateManager;
import com.petra.lib.manager.state.JobState;
import com.petra.lib.signal.ResponseSignal;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
public class ErrorState implements JobStateManager {

    private final ResponseSignal responseSignal;

    @Override
    public void execute(JobContext jobContext) throws Exception {
        UUID scenarioId = jobContext.getScenarioId();
        responseSignal.setError(scenarioId);
    }

    @Override
    public JobState getManagerState() {
        return JobState.ERROR;
    }

    @Override
    public void start() {

    }
}
