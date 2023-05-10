package com.petra.lib.worker.error;

import com.petra.lib.manager.block.JobContext;
import com.petra.lib.manager.state.JobStateManager;
import com.petra.lib.manager.state.JobState;
import com.petra.lib.queue.InputQueue;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;

import java.util.UUID;

@Log4j2
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class ErrorState implements JobStateManager {

    InputQueue inputQueue;
    String blockName;

    @Override
    public void execute(JobContext jobContext) throws Exception {
        log.error("FIND Error in execution {} {}", blockName, jobContext.toString());
        UUID scenarioId = jobContext.getScenarioId();
        inputQueue.setError(scenarioId, jobContext.getRequestSourceId());
    }

    @Override
    public JobState getManagerState() {
        return JobState.ERROR;
    }

    @Override
    public void start() {

    }
}
