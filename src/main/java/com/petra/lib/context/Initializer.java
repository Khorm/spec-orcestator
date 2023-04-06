package com.petra.lib.context;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.petra.lib.manager.ExecutionContext;
import com.petra.lib.manager.ExecutionHandler;
import com.petra.lib.manager.ExecutionStateManager;
import com.petra.lib.manager.state.ExecutionState;
import com.petra.lib.registration.ExecutionRepository;
import com.petra.lib.variable.process.ProcessVariable;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

import java.util.Collection;

/**
 * Менеджер управляет инициализацией запуска блока и проверкой блока на предыдущее исполнение
 */
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class Initializer implements ExecutionStateManager {

    Long blockId;
    ExecutionHandler executionHandler;
    ExecutionRepository executionRepository;

    public Initializer(Long blockId, ExecutionHandler executionHandler, ExecutionRepository executionRepository){
        this.blockId = blockId;
        this.executionRepository = executionRepository;
        this.executionHandler = executionHandler;
    }


    @Override
    public void execute(ExecutionContext executionContext) throws JsonProcessingException {
        boolean isExecutedBefore = executionRepository.isExecutedBefore(executionContext.getScenarioId(), blockId);
        if (isExecutedBefore){
            executionContext.setLoadVariables(executionRepository.getVariables(executionContext.getScenarioId(),blockId));
            executionHandler.executeState(executionContext, ExecutionState.EXECUTION_RESPONSE);
            return;
        }
        Collection<ProcessVariable> signalVariables = executionContext.getSignalVariables();
        executionContext.setSignalVariables(signalVariables);
        executionHandler.executeNext(executionContext, getManagerState());
    }

    @Override
    public ExecutionState getManagerState() {
        return ExecutionState.INITIALIZING;
    }

    @Override
    public void start() {
        //do nothin
    }

}
