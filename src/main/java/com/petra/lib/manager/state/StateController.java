package com.petra.lib.manager.state;

import com.petra.lib.manager.block.JobStateManager;

import java.util.Optional;

@Deprecated
public interface StateController {

    Optional<JobState> getNextState(JobState prevState);
    JobStateManager getState(JobState state);
    void registerManager(JobState jobState, JobStateManager manager);

    static StateController createStateController() {
        return new StateControllerImpl();
    }
}
