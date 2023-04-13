package com.petra.lib.registration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.petra.lib.manager.block.ExecutionContext;
import com.petra.lib.manager.block.ExecutionHandler;
import com.petra.lib.manager.block.ExecutionStateManager;
import com.petra.lib.manager.state.ExecutionState;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.UUID;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RegistrationState implements ExecutionStateManager {

    ExecutionRepository executionRepository;
    Long blockId;
    ExecutionHandler executionHandler;
    PlatformTransactionManager transactionManager;

    @Override
    public void start() {

    }

    public RegistrationState(Long blockId, ExecutionHandler executionHandler,
                             ExecutionRepository executionRepository, PlatformTransactionManager transactionManager) {
        this.blockId = blockId;
        this.executionRepository = executionRepository;
        this.executionHandler = executionHandler;
        this.transactionManager = transactionManager;
    }

    @Override
    public void execute(ExecutionContext executionContext) throws JsonProcessingException {
        UUID scenarioId = executionContext.getScenarioId();
        String variables = executionContext.getVariablesJson();
        executionRepository.saveExecution(scenarioId, blockId, variables);
        transactionManager.commit(executionContext.getTransactionStatus());
        executionHandler.executeNext(executionContext, getManagerState());

    }

    @Override
    public ExecutionState getManagerState() {
        return ExecutionState.EXECUTION_REGISTRATION;
    }

}
