package com.petra.lib.context;

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

    public Initializer(Long blockId, ExecutionHandler executionHandler){
        this.blockId = blockId;
        this.executionRepository = new ExecutionRepository();
        this.executionHandler = executionHandler;
    }


    @Override
    public void execute(ExecutionContext executionContext) {
        boolean isExecutedBefore = executionRepository.isExecutedBefore(executionContext.getScenarioId(), blockId);
        if (isExecutedBefore){
            return;
        }
        Collection<ProcessVariable> signalVariables = executionContext.getSignalVariables();
        executionContext.setVariables(signalVariables);
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
