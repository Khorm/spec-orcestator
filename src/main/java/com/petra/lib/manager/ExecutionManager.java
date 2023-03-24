package com.petra.lib.manager;

import com.petra.lib.manager.state.ExecutionState;
import com.petra.lib.manager.state.StateController;
import com.petra.lib.signal.Signal;
import com.petra.lib.signal.model.SignalTransferModel;
import com.petra.lib.variable.base.VariableList;
import com.petra.lib.variable.mapper.VariableMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;

import java.util.Map;


/**
 * Класс управляющий вызовом любого исполняемого блока
 */
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Log4j2
class ExecutionManager implements ExecutionHandler {

    /**
     * список стейтменеджеров с применеными стейтами
     */
    Map<ExecutionState, ExecutionStateManager> stateList;

    /**
     * список переменных блока
     */
    VariableList variableList;

    /**
     * Управление менеджерами
     */
    ThreadManager threadManager;

    /**
     * маппер от входящего сигнала
     */
    VariableMapper inputMapper;

    /**
     * входящие сообщения
     */
    Signal inputSignal;

    /**
     * контроллер переключения стейтов
     */
    StateController stateController;


    private void start(SignalTransferModel signalTransferModel, Integer group) {
        ExecutionContext executionContext = new ExecutionContext(variableList, inputMapper, signalTransferModel, group);
        ExecutionStateManager firstState = stateList.get(ExecutionState.INITIALIZING);
        executeTask(executionContext, firstState);
    }

    public void executeNext(ExecutionContext executionContext, ExecutionState executedState) {
//        ExecutionState executedState = executedStateManager.getManagerState();
        ExecutionStateManager nextExecState = stateList.get(stateController.getNextState(executedState));
        executeTask(executionContext, nextExecState);
    }


    private void executeTask(ExecutionContext executionContext, ExecutionStateManager task) {
        ExecutionState executionState = task.getManagerState();
        if (executionState.isExecuteInNewThread()) {
            threadManager.execute(() -> {
                try {
                    task.execute(executionContext);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        } else {
            try {
                task.execute(executionContext);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
