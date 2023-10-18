package com.petra.lib.state.response;

import com.petra.lib.block.BlockId;
import com.petra.lib.XXXXXcontext.ActionContext;
import com.petra.lib.XXXXXXsignal.SignalId;
import com.petra.lib.XXXXXXsignal.response.controller.ResponseReadyListener;
import com.petra.lib.variable.mapper.VariableMapper;
import com.petra.lib.XXXXXmanager.state.JobStateManager;
import com.petra.lib.block.ProcessValue;
import com.petra.lib.state.repo.JobRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Log4j2
public class ResponseState implements JobStateManager {

    Map<SignalId, VariableMapper> answerMappers;
    String blockName;
    ResponseReadyListener responseReadyListener;
    BlockId blockId;
    JobRepository jobRepository;

    @Override
    public void execute(ActionContext actionContext) throws Exception {
        log.debug("START ResponseState {} {}", blockName, actionContext.getScenarioId());
        UUID scenarioId = actionContext.getScenarioId();
        VariableMapper mapperForResponseSignal = answerMappers.get(actionContext.getRequestSignalId());
        if (actionContext.isAlreadyDone()){
            Collection<ProcessValue> answerVariableList = mapperForResponseSignal.map(jobRepository.getVariables(actionContext.getScenarioId(), blockId));
            responseReadyListener.idempotencyErrorToRequest(answerVariableList, actionContext.getRequestSignalId(), actionContext.getRequestBlockId(), scenarioId, blockId);
        }else {
            Collection<ProcessValue> answerVariableList = mapperForResponseSignal.map(actionContext.getProcessVariables());
            responseReadyListener.answerToRequest(answerVariableList, responseSignalId, actionContext.getRequestBlockId(), scenarioId, blockId);
        }
        log.debug("END ResponseState {} {}", blockName, actionContext.getScenarioId());
    }

    @Override
    public JobState getManagerState() {
        return JobState.EXECUTION_RESPONSE;
    }

    @Override
    public void start() {
    }
}
