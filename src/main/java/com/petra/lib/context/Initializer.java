package com.petra.lib.context;

import com.petra.lib.manager.state.ExecutionBehavior;
import com.petra.lib.manager.ExecutionContext;
import com.petra.lib.manager.ExecutionHandler;
import com.petra.lib.manager.ExecutionStateManager;
import com.petra.lib.variable.mapper.VariableMapper;
import com.petra.lib.variable.process.ProcessVariable;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.Collection;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class Initializer implements ExecutionStateManager {
    VariableMapper enterVariableMapper;
    InitializationRepository initializationRepository;


    @Override
    public void execute(ExecutionContext executionContext, ExecutionHandler executionHandler) {
        initializationRepository.saveStarterSignal(executionContext.getEnterSignalTransferModel());
        Collection<ProcessVariable> signalVariables = executionContext.getSignalVariables();
        Collection<ProcessVariable> blockVariables = enterVariableMapper.map(signalVariables);
        executionContext.setVariables(blockVariables);
        executionHandler.executeNext(executionContext, this, ExecutionBehavior.NEXT);
    }
}
