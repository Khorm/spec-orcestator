package com.petra.lib.action.execution;

import com.petra.lib.action.new_action.ActionHandler;
import com.petra.lib.action.new_action.ActionRepository;
import com.petra.lib.action.new_action.ExecutionResult;
import com.petra.lib.manager.state.ExecutionBehavior;
import com.petra.lib.manager.ExecutionContext;
import com.petra.lib.manager.ExecutionHandler;
import com.petra.lib.manager.ExecutionState;
import com.petra.lib.variable.base.VariableList;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

/**
 * Класс выполняет функционал обработки активности
 */
public class ActionExecutionState implements ExecutionState {

    /**
     * Менеджер транзакций
     */
    PlatformTransactionManager txManager;
    ActionHandler actionHandler;
    ActionRepository actionRepository;
    VariableList compensationVariableList;
    VariableList actionVariableList;

    @Override
    public void execute(ExecutionContext executionContext, ExecutionHandler executionHandler) {
        TransactionStatus status = null;
        try {
            DefaultTransactionDefinition def = new DefaultTransactionDefinition();
            status = txManager.getTransaction(def);
            ActionContextImpl actionContext = new ActionContextImpl(actionVariableList, executionContext, compensationVariableList);
            actionHandler.execute(actionContext);
            actionRepository.saveExecutionResult(actionContext.getExecutionContext(), ExecutionResult.OK);
            executionHandler.executeNext(executionContext, this, ExecutionBehavior.NEXT);
        } catch (Exception e) {
            if (status != null) {
                txManager.rollback(status);
            }
            actionRepository.saveExecutionResult(executionContext, ExecutionResult.ERROR);
            throw e;
        }
    }
}
