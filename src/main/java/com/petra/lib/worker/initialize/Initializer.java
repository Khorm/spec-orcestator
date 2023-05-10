package com.petra.lib.worker.initialize;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.petra.lib.manager.block.JobContext;
import com.petra.lib.manager.block.JobStaticManager;
import com.petra.lib.manager.block.ProcessVariableDto;
import com.petra.lib.manager.state.JobState;
import com.petra.lib.manager.state.JobStateManager;
import com.petra.lib.signal.ResponseSignal;
import com.petra.lib.worker.repo.JobRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;

import java.util.Collection;

/**
 * Manage the start of block execution. Initialize execution context and check previous executions.
 */
@Log4j2
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class Initializer implements JobStateManager {

    /**
     * Block id, which initializer belongs.
     */
    Long blockId;
    String blockName;
    JobStaticManager jobStaticManager;
    JobRepository jobRepository;


    @Override
    public void execute(JobContext jobContext) throws JsonProcessingException {
        boolean isExecutedBefore = jobRepository.isExecutedBefore(jobContext.getScenarioId(), blockId);

        if (isExecutedBefore) {
            log.debug("Job already executed {} {}", blockName, jobContext.toString());
            Collection<ProcessVariableDto> savedVariables = jobRepository.getVariables(jobContext.getScenarioId(), blockId);
            savedVariables.forEach(jobContext::setVariable);
            jobStaticManager.executeState(jobContext, JobState.EXECUTION_RESPONSE);
            return;
        }
        log.debug("Job starts executed {} {}", blockName, jobContext.toString());
        jobStaticManager.executeState(jobContext, JobState.REQUEST_SOURCE_DATA);
    }

    @Override
    public JobState getManagerState() {
        return JobState.INITIALIZING;
    }

    @Override
    public void start() {

    }
}
