package com.petra.lib.workflow.variables;

import com.petra.lib.variable.mapper.VariableMapper;
import com.petra.lib.variable.value.VariablesContainer;
import com.petra.lib.workflow.context.WorkflowActionContext;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * Загрузчик переменных перед исполнением блока
 */
public class LoadVariablesManager {

    Map<Long, VariableMapper> variableMappersBySignalIds;

    /**
     * должна быть сгруппирована по номерам груп от 0
     */
    Collection<LoadGroup> loadGroups;

    public void start(WorkflowActionContext workflowActionContext) throws ExecutionException, InterruptedException {
        //передлываем переменные входящего сигнала в переменные активности
        VariablesContainer blockContainer =
                variableMappersBySignalIds.get(workflowActionContext.getCurrentSignalId())
                        .map(workflowActionContext.getSignalVariables());
        workflowActionContext.setBlockVariables(blockContainer);

        //запрашивает сигналы у соурсов
        for (LoadGroup loadGroup : loadGroups) {
            loadGroup.load(workflowActionContext);
        }
    }

}
