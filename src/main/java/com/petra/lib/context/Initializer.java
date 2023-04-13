package com.petra.lib.context;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.petra.lib.manager.block.ExecutionContext;
import com.petra.lib.manager.block.ExecutionHandler;
import com.petra.lib.manager.block.ExecutionStateManager;
import com.petra.lib.manager.state.ExecutionState;
import com.petra.lib.registration.ExecutionRepository;
import com.petra.lib.signal.ResponseSignal;
import com.petra.lib.signal.SignalListener;
import com.petra.lib.signal.model.SignalTransferModel;
import com.petra.lib.variable.process.ProcessVariable;
import lombok.AccessLevel;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.Collection;

/**
 * Менеджер управляет инициализацией запуска блока и проверкой блока на предыдущее исполнение
 */
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Initializer implements ExecutionStateManager, SignalListener {

    final Long blockId;
    final ExecutionHandler executionHandler;
    final ExecutionRepository executionRepository;

    @Setter
    ResponseSignal blockResponseSignal;


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

    @Override
    public void executeSignal(SignalTransferModel request) {

    }
}
