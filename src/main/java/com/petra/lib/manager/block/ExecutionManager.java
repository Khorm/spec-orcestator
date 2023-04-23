package com.petra.lib.manager.block;

import com.petra.lib.manager.state.ExecutionState;
import com.petra.lib.manager.state.StateController;
import com.petra.lib.manager.thread.ThreadManager;
import com.petra.lib.signal.model.SignalTransferModel;
import com.petra.lib.variable.base.VariableList;
import com.petra.lib.variable.mapper.VariableMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.Optional;


/**
 * Класс управляющий вызовом любого исполняемого блока
 */
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Log4j2
public class ExecutionManager implements ExecutionHandler {

    /**
     * список переменных блока
     */
    VariableList variableList;

    /**
     * маппер от входящего сигнала
     */
    VariableMapper inputMapper;

    /**
     * Управление менеджерами
     */
//    ThreadManager threadManager;

    /**
     * контроллер переключения стейтов
     */
    StateController stateController;

    PlatformTransactionManager transactionManager;


    private void start(SignalTransferModel signalTransferModel) {
        DefaultTransactionDefinition definition = new DefaultTransactionDefinition();
        definition.setIsolationLevel(TransactionDefinition.ISOLATION_REPEATABLE_READ);
        definition.setTimeout(3);
        TransactionStatus transactionStatus = transactionManager.getTransaction(definition);

        ExecutionContext executionContext = new ExecutionContext(variableList, inputMapper, signalTransferModel, transactionStatus);
        ExecutionStateManager firstState = stateController.getState(ExecutionState.INITIALIZING);
        executeTask(executionContext, firstState);
    }

    public void executeNext(ExecutionContext executionContext, ExecutionState executedState) {
        Optional<ExecutionState> nextState = stateController.getNextState(executedState);
        if (nextState.isPresent()) {
            executeState(executionContext, nextState.get());
        }
    }

    @Override
    public void executeState(ExecutionContext executionContext, ExecutionState executedState) {
        ExecutionStateManager executionStateManager = stateController.getState(executedState);
        executeTask(executionContext, executionStateManager);
    }


    private void executeTask(ExecutionContext executionContext, ExecutionStateManager task) {
        ExecutionState executionState = task.getManagerState();
        if (executionState.isExecuteInNewThread()) {
            ThreadManager.execute(() -> {
                try {
                    task.execute(executionContext);
                } catch (Exception e) {
                    e.printStackTrace();
                    transactionManager.rollback(executionContext.getTransactionStatus());
                }
            });
        } else {
            try {
                task.execute(executionContext);
            } catch (Exception e) {
                e.printStackTrace();
                transactionManager.rollback(executionContext.getTransactionStatus());
            }
        }
    }
}
