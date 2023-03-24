package com.petra.lib.registration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.petra.lib.manager.ExecutionContext;
import com.petra.lib.manager.ExecutionStateManager;
import com.petra.lib.manager.state.ExecutionState;
import com.petra.lib.signal.Signal;

import java.util.UUID;

class RegistrationState implements ExecutionStateManager {

    ExecutionRepository executionRepository;
    Long blockId;
    Signal signal;

    @Override
    public void execute(ExecutionContext executionContext) throws JsonProcessingException {
        UUID scenarioId = executionContext.getScenarioId();
        Integer groupId = executionContext.getGroup();
        String variables = executionContext.getVariablesJson();
        executionRepository.saveExecution(scenarioId, blockId, groupId, variables);

        signal.accept();
    }

    @Override
    public ExecutionState getManagerState() {
        return ExecutionState.EXECUTION_REGISTRATION;
    }
}
