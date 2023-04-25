package com.petra.lib.manager.block;

import com.petra.lib.manager.state.JobState;

public interface JobStateManager {
    void execute(JobContext jobContext) throws Exception;
    JobState getManagerState();
    void start();

}
