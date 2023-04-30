package com.petra.lib.manager.state;

import com.petra.lib.manager.block.JobContext;
import com.petra.lib.manager.block.JobStaticManager;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;

import java.util.HashMap;
import java.util.Map;


/**
 * Класс управляющий вызовом любого исполняемого блока
 */
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Log4j2
public class JobStaticManagerImpl implements JobStaticManager {

    Map<JobState, JobStateManager> jobStateJobStateManagerMap = new HashMap<>();

    JobStaticManagerImpl() {
    }

    @Override
    public void executeState(JobContext jobContext, JobState executedState) {
        JobStateManager jobStateManager = jobStateJobStateManagerMap.get(executedState);
        try {
            jobStateManager.execute(jobContext);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e);
            try {
                jobStateJobStateManagerMap.get(JobState.ERROR).execute(jobContext);
            } catch (Exception ex) {
                ex.printStackTrace();
                log.error(ex);
                throw new Error("Critical error in error handler", ex);
            }
        }
    }

    @Override
    public void setStateManager(JobStateManager jobStateManager) {
        jobStateJobStateManagerMap.put(jobStateManager.getManagerState(), jobStateManager);
    }

    @Override
    public void start() {
        jobStateJobStateManagerMap.values().forEach(manager -> manager.start());
    }

}
