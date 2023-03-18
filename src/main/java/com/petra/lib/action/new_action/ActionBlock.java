package com.petra.lib.action.new_action;

import com.petra.lib.action.execution.ActionContextImpl;
import com.petra.lib.block.ExecutingBlock;
import com.petra.lib.manager.ExecutionContext;
import com.petra.lib.signal.source.SourceHandler;
import com.petra.lib.variable.base.VariableList;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.function.Consumer;

/**
 * Блок активности
 */
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class ActionBlock implements ExecutingBlock {

    SourceHandler sourceHandler;
    ActionRepository actionRepository;
    /**
     * Менеджер транзакций
     */
    PlatformTransactionManager txManager;
    Consumer<ExecutionContext> afterExecuteConsumer;
    Consumer<ExecutionContext> errorExecutionConsumer;
    ActionHandler actionHandler;

    /**
     * Список принадлкжащих активности переменных
     */
    VariableList variableList;


    @Override
    public void execute(ExecutionContext executionContext) {


//        проверка, выполнялась ли активность ранее
//        если выполнялась то вернуть результат
        Boolean isActionExecutedBefore = actionRepository.isActionExecutedBefore(executionContext.getScenarioId(), executionContext.getBlockId());
        if (isActionExecutedBefore) {
//            scenarioObserverManager.execute(actionRepository.getExecutionResult(request.getScenarioId(), getBlockId()));
            afterExecuteConsumer.accept(actionRepository.getExecutionResult(executionContext.getScenarioId(), executionContext.getBlockId()));
            return;
        }
        sourceHandler.execute(executionContext);

    }

    void afterSourcesSet(ExecutionContext executionContext) {
        TransactionStatus status = null;
        try {
            DefaultTransactionDefinition def = new DefaultTransactionDefinition();
            status = txManager.getTransaction(def);
            ActionContextImpl actionContext = new ActionContextImpl(executionContext, variableList);
            actionHandler.execute(actionContext);
            actionRepository.saveExecutionResult(actionContext.getExecutionContext(), ExecutionResult.OK);
        } catch (Exception e) {
            if (status != null) {
                txManager.rollback(status);
            }
            actionRepository.saveExecutionResult(executionContext, ExecutionResult.ERROR);
            errorExecutionConsumer.accept(executionContext);
        }
    }

    void sourcesSetError(ExecutionContext executionContext) {
        actionRepository.saveExecutionResult(executionContext, ExecutionResult.ERROR);
        errorExecutionConsumer.accept(executionContext);
    }

}
