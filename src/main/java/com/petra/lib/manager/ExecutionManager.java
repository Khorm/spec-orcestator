package com.petra.lib.manager;

import com.petra.lib.manager.state.ExecutionBehavior;
import com.petra.lib.signal.model.SignalTransferModel;
import com.petra.lib.variable.base.VariableList;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;

import java.util.Iterator;
import java.util.List;


/**
 * Класс управляющий вызовом любого исполняемого блока
 */
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Log4j2
public class ExecutionManager {

    List<ExecutionStateManager> stateList;
    VariableList variableList;
    ThreadManager threadManager;

    private void start(SignalTransferModel signalTransferModel){
        ExecutionContext executionContext = new ExecutionContext(variableList, signalTransferModel);
        stateList.get(0).execute(executionContext, this::executeNext);
    }

    private void executeNext(ExecutionContext executionContext, ExecutionStateManager executedState, ExecutionBehavior executionBehavior){
        Iterator<ExecutionStateManager> iter = stateList.iterator();
        while (iter.hasNext()){
            ExecutionStateManager executionStateManager = iter.next();
            if (executionStateManager.equals(executedState)){
                if (iter.hasNext() && executionBehavior == ExecutionBehavior.NEXT) {
                    ExecutionStateManager nextExecutionStateManager = iter.next();
                    executeTask(executionContext, nextExecutionStateManager);
                }
                return;
            }
        }
    }


    private void executeTask(ExecutionContext executionContext, ExecutionStateManager task){
        threadManager.execute(() -> {
            try {
                task.execute(executionContext, this::executeNext);
            }catch (Exception e){
                e.printStackTrace();
            }
        });
    }
}
