package com.petra.lib.manager.block;

import com.petra.lib.manager.state.JobState;
import com.petra.lib.manager.state.JobStateManager;
import com.petra.lib.variable.mapper.VariableMapper;

import java.util.Collection;

public interface JobStaticManager {
    void executeState(JobContext jobContext, JobState executedState);
    void putStateManager(JobStateManager jobStateManagers);
    void start();

    static JobStaticManager createJobStaticManager(Long id, String blockName, Collection<Long> listeningSignalIds, VariableMapper inputMapper){
        return new JobStaticManagerImpl(id, blockName, listeningSignalIds, inputMapper);
    }
}
