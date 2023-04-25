package com.petra.lib.manager.state;

import com.petra.lib.manager.block.JobStateManager;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

final class StateControllerImpl implements StateController {

    private final Map<JobState, JobState> stateInheritance = new EnumMap<>(JobState.class);
    /**
     * список стейтменеджеров с применеными стейтами
     */

    private final Map<JobState, JobStateManager> stateList = new HashMap<>();

    StateControllerImpl() {
        stateInheritance.put(JobState.INITIALIZING, JobState.REQUEST_SOURCE_DATA);
        stateInheritance.put(JobState.REQUEST_SOURCE_DATA, JobState.EXECUTING);
        stateInheritance.put(JobState.EXECUTING, JobState.EXECUTION_REGISTRATION);
        stateInheritance.put(JobState.EXECUTION_REGISTRATION, JobState.EXECUTION_RESPONSE);
    }

    @Override
    public void registerManager(JobState jobState, JobStateManager manager) {
        stateList.put(jobState, manager);
    }

    @Override
    public Optional<JobState> getNextState(JobState prevState) {
        return Optional.ofNullable(stateInheritance.get(prevState));
    }

    @Override
    public JobStateManager getState(JobState state) {
        return stateList.get(state);
    }
}
