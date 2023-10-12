package com.petra.lib.state.error;

import com.petra.lib.block.BlockId;
import com.petra.lib.context.ActionContext;
import com.petra.lib.manager.state.JobStateManager;
import com.petra.lib.signal.SignalId;
import com.petra.lib.signal.response.controller.ResponseReadyListener;
import com.petra.lib.state.StateHandler;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;

import java.util.UUID;

@Log4j2
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class ErrorState implements StateHandler {

    ResponseReadyListener responseReadyListener;
    String blockName;
    BlockId blockId;
    SignalId responseSignalId;

    @Override
    public void execute(ActionContext actionContext) throws Exception {
        log.error("FIND Error in execution {} {}", blockName, actionContext.toString());
        UUID scenarioId = actionContext.getScenarioId();
        responseReadyListener.errorToRequest(responseSignalId, actionContext.getRequestBlockId(), scenarioId, blockId);
    }

    @Override
    public JobState getManagerState() {
        return JobState.ERROR;
    }

    @Override
    public void start() {

    }
}
