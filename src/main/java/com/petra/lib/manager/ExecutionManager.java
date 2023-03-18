package com.petra.lib.manager;

import com.petra.lib.manager.state.ExecutionBehavior;
import com.petra.lib.signal.model.SignalModel;
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

    List<ExecutionState> stateList;
    VariableList variableList;
    ThreadManager threadManager;

    private void start(SignalModel signalModel){
        ExecutionContext executionContext = new ExecutionContext(variableList, signalModel);
        stateList.get(0).execute(executionContext, this::executeNext);
    }

    private void executeNext(ExecutionContext executionContext, ExecutionState executedState, ExecutionBehavior executionBehavior){
        Iterator<ExecutionState> iter = stateList.iterator();
        while (iter.hasNext()){
            ExecutionState executionState = iter.next();
            if (executionState.equals(executedState)){
                if (iter.hasNext() && executionBehavior == ExecutionBehavior.NEXT) {
                    ExecutionState nextExecutionState = iter.next();
                    executeTask(executionContext, nextExecutionState);
                }
                return;
            }
        }
    }


    private void executeTask(ExecutionContext executionContext, ExecutionState task){
        threadManager.execute(() -> {
            try {
                task.execute(executionContext, this::executeNext);
            }catch (Exception e){
                e.printStackTrace();
            }
        });
    }
}
