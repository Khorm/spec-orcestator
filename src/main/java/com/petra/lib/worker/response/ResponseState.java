package com.petra.lib.worker.response;

import com.petra.lib.manager.block.JobContext;
import com.petra.lib.variable.mapper.VariableMapper;
import com.petra.lib.manager.state.JobStateManager;
import com.petra.lib.manager.state.JobState;
import com.petra.lib.signal.ResponseSignal;
import com.petra.lib.manager.block.ProcessVariableDto;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.Collection;
import java.util.UUID;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class ResponseState implements JobStateManager {

    ResponseSignal responseSignal;
    VariableMapper answerMapper;

    @Override
    public void execute(JobContext jobContext) throws Exception {
        UUID scenarioId = jobContext.getScenarioId();
        Collection<ProcessVariableDto> blockVariableList = jobContext.getProcessVariables();
        Collection<ProcessVariableDto> answerVariableList = answerMapper.map(blockVariableList);
        responseSignal.setAnswer(answerVariableList, scenarioId);
    }

    @Override
    public JobState getManagerState() {
        return JobState.EXECUTION_RESPONSE;
    }

    @Override
    public void start() {
    }
}
