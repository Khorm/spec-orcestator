package com.petra.lib.registration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.petra.lib.manager.ExecutionContext;
import com.petra.lib.manager.ExecutionHandler;
import com.petra.lib.manager.ExecutionStateManager;
import com.petra.lib.manager.state.ExecutionState;
import com.petra.lib.signal.Signal;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RegistrationState implements ExecutionStateManager {

    ExecutionRepository executionRepository;
    Long blockId;
    ExecutionHandler executionHandler;

    public RegistrationState(Long blockId, ExecutionHandler executionHandler){
        this.blockId = blockId;
        this.executionRepository = new ExecutionRepository();
        this.executionHandler = executionHandler;
    }

    @Override
    public void execute(ExecutionContext executionContext) throws JsonProcessingException {
        UUID scenarioId = executionContext.getScenarioId();
        Integer groupId = executionContext.getGroup();
        String variables = executionContext.getVariablesJson();
        executionRepository.saveExecution(scenarioId, blockId, groupId, variables);
        executionHandler.executeNext(executionContext, getManagerState());
    }

    @Override
    public ExecutionState getManagerState() {
        return ExecutionState.EXECUTION_REGISTRATION;
    }
}
