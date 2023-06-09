package com.petra.lib.worker.error;

import com.petra.lib.manager.block.BlockId;
import com.petra.lib.manager.block.JobContext;
import com.petra.lib.manager.state.JobStateManager;
import com.petra.lib.manager.state.JobState;
import com.petra.lib.queue.TaskQueueManager;
import com.petra.lib.signal.SignalId;
import com.petra.lib.signal.response.controller.ResponseReadyListener;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;

import java.util.UUID;

@Log4j2
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class ErrorState implements JobStateManager {

    ResponseReadyListener responseReadyListener;
    String blockName;
    BlockId blockId;
    SignalId responseSignalId;

    @Override
    public void execute(JobContext jobContext) throws Exception {
        log.error("FIND Error in execution {} {}", blockName, jobContext.toString());
        UUID scenarioId = jobContext.getScenarioId();
        responseReadyListener.errorToRequest(responseSignalId, jobContext.getRequestBlockId(), scenarioId, blockId);
    }

    @Override
    public JobState getManagerState() {
        return JobState.ERROR;
    }

    @Override
    public void start() {

    }
}
