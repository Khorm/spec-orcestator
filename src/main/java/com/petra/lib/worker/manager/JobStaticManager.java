package com.petra.lib.worker.manager;

import com.petra.lib.manager.block.JobContext;
import com.petra.lib.manager.block.JobStateManager;
import com.petra.lib.manager.state.JobState;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.Collection;

public interface JobStaticManager {
    void executeNext(JobContext executionContext, JobState executedState);
    void executeState(JobContext jobContext, JobState executedState);
    void setStateManager(JobStateManager jobStateManagers);
    void start();

    static JobStaticManager createJobStaticManager(PlatformTransactionManager transactionManager){
        return new JobStaticManagerImpl(transactionManager);
    }
}
