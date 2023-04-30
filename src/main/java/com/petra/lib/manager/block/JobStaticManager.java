package com.petra.lib.manager.block;

import com.petra.lib.manager.state.JobState;
import com.petra.lib.manager.state.JobStateManager;
import com.petra.lib.manager.state.JobStaticManagerImpl;

public interface JobStaticManager {
//    void executeNext(JobContext executionContext, JobState executedState);
    void executeState(JobContext jobContext, JobState executedState);
//    void executeStateAsync(JobContext jobContext, JobState executedState);
    void setStateManager(JobStateManager jobStateManagers);
    void start();

    static JobStaticManager createJobStaticManager(){
        return new JobStaticManagerImpl();
    }
}
