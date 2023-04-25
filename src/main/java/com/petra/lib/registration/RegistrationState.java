package com.petra.lib.registration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.petra.lib.manager.block.JobContext;
import com.petra.lib.worker.manager.JobStaticManager;
import com.petra.lib.manager.block.JobStateManager;
import com.petra.lib.manager.state.JobState;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.UUID;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RegistrationState implements JobStateManager {

    JobRepository jobRepository;
    Long blockId;
    JobStaticManager jobStaticManager;
    PlatformTransactionManager transactionManager;

    @Override
    public void start() {

    }

    public RegistrationState(Long blockId, JobStaticManager jobStaticManager,
                             JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        this.blockId = blockId;
        this.jobRepository = jobRepository;
        this.jobStaticManager = jobStaticManager;
        this.transactionManager = transactionManager;
    }

    @Override
    public void execute(JobContext jobContext) throws JsonProcessingException {
        UUID scenarioId = jobContext.getScenarioId();
        String variables = jobContext.getVariablesJson();
        jobRepository.saveExecution(scenarioId, blockId, variables);
        transactionManager.commit(jobContext.getTransactionStatus());
        jobStaticManager.executeNext(jobContext, getManagerState());

    }

    @Override
    public JobState getManagerState() {
        return JobState.EXECUTION_REGISTRATION;
    }

}
