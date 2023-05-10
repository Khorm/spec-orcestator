package com.petra.lib.worker.response;

import com.petra.lib.manager.block.JobContext;
import com.petra.lib.queue.InputQueue;
import com.petra.lib.variable.mapper.VariableMapper;
import com.petra.lib.manager.state.JobStateManager;
import com.petra.lib.manager.state.JobState;
import com.petra.lib.manager.block.ProcessVariableDto;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;

import java.util.Collection;
import java.util.UUID;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Log4j2
public class ResponseState implements JobStateManager {

    InputQueue inputQueue;
    VariableMapper answerMapper;
    String blockName;

    @Override
    public void execute(JobContext jobContext) throws Exception {
        log.debug("START ResponseState {} {}", blockName, jobContext.toString());
        UUID scenarioId = jobContext.getScenarioId();
        Collection<ProcessVariableDto> blockVariableList = jobContext.getProcessVariables();
        Collection<ProcessVariableDto> answerVariableList = answerMapper.map(blockVariableList);
        inputQueue.setAnswer(answerVariableList, scenarioId, jobContext.getRequestSourceId());
        log.debug("END ResponseState {} {}", blockName, jobContext.toString());
    }

    @Override
    public JobState getManagerState() {
        return JobState.EXECUTION_RESPONSE;
    }

    @Override
    public void start() {
    }
}
