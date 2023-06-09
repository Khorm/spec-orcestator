package com.petra.lib.worker.response;

import com.petra.lib.manager.block.BlockId;
import com.petra.lib.manager.block.JobContext;
import com.petra.lib.queue.TaskQueueManager;
import com.petra.lib.signal.SignalId;
import com.petra.lib.signal.response.controller.ResponseReadyListener;
import com.petra.lib.variable.mapper.VariableMapper;
import com.petra.lib.manager.state.JobStateManager;
import com.petra.lib.manager.state.JobState;
import com.petra.lib.manager.block.ProcessVariableDto;
import com.petra.lib.worker.repo.JobRepository;
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
    public void execute(JobContext jobContext) throws Exception {
        log.debug("START ResponseState {} {}", blockName, jobContext.getScenarioId());
        UUID scenarioId = jobContext.getScenarioId();
        VariableMapper mapperForResponseSignal = answerMappers.get(jobContext.getRequestSignalId());
        if (jobContext.isAlreadyDone()){
            Collection<ProcessVariableDto> answerVariableList = mapperForResponseSignal.map(jobRepository.getVariables(jobContext.getScenarioId(), blockId));
            responseReadyListener.idempotencyErrorToRequest(answerVariableList, jobContext.getRequestSignalId(), jobContext.getRequestBlockId(), scenarioId, blockId);
        }else {
            Collection<ProcessVariableDto> answerVariableList = mapperForResponseSignal.map(jobContext.getProcessVariables());
            responseReadyListener.answerToRequest(answerVariableList, responseSignalId, jobContext.getRequestBlockId(), scenarioId, blockId);
        }
        log.debug("END ResponseState {} {}", blockName, jobContext.getScenarioId());
    }

    @Override
    public JobState getManagerState() {
        return JobState.EXECUTION_RESPONSE;
    }

    @Override
    public void start() {
    }
}
