package com.petra.lib.worker.initialize;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.petra.lib.manager.block.*;
import com.petra.lib.manager.models.BlockModel;
import com.petra.lib.manager.state.JobState;
import com.petra.lib.manager.state.JobStateManager;
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
public class Initializer implements JobStateManager {

    /**
     * Block id
     */
    BlockId blockId;
    String blockName;
    JobStaticManager jobStaticManager;
    JobRepository jobRepository;

    public Initializer(BlockModel blockModel, JobStaticManager jobStaticManager, JobRepository jobRepository){
        this.blockId = new BlockId(blockModel.getId(), blockModel.getVersion());
        this.blockName = blockModel.getName();
        this.jobStaticManager = jobStaticManager;
        this.jobRepository = jobRepository;
    }


    @Override
    public void execute(JobContext jobContext) throws JsonProcessingException {
        boolean isExecutedBefore = jobRepository.isExecutedBefore(jobContext.getScenarioId(), blockId);

        if (isExecutedBefore) {
            log.debug("Job already executed {} {}", blockName, jobContext.toString());
            jobContext.setAlreadyDone();
            jobStaticManager.executeState(jobContext, JobState.EXECUTION_RESPONSE);
            return;
        }
        log.debug("Job starts executed {} {}", blockName, jobContext.toString());
        jobStaticManager.executeState(jobContext, JobState.FILL_CONTEXT_VARIABLES);
    }

    @Override
    public JobState getManagerState() {
        return JobState.INITIALIZING;
    }

    @Override
    public void start() {

    }
}
