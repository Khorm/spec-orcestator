package com.petra.lib.workflow.block;

import com.petra.lib.workflow.context.WorkflowActionContext;
import com.petra.lib.workflow.enums.WorkflowActionState;
import com.petra.lib.workflow.context.WorkflowContext;
import com.petra.lib.workflow.repo.WorkflowActionRepo;
import com.petra.lib.workflow.signal.Signal;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Управляет запуском следующих блоков
 */
public class BlockManager {

    Map<Long, Collection<ExecutionBlock>> blocksBySignals = new HashMap<>();
    WorkflowActionRepo workflowActionRepo;

    /**
     * Обрабатывает входящий сигнал активностей
     * @param workflowContext
     * @param signal
     */
    public void execute(WorkflowContext workflowContext, Signal signal) {
        for (ExecutionBlock executionBlock : blocksBySignals.get(workflowContext.getSignalId())) {
            WorkflowActionContext workflowActionContext = new WorkflowActionContext(
                    workflowContext.getScenarioId(),
                    workflowContext.getWorkflowId(),
                    executionBlock.getId(),
                    signal.getSignalId(),
                    signal.getSignalVariables()
            );
            workflowActionContext.setWorkflowState(WorkflowActionState.LOAD_VARIABLES);

            boolean isContextSaved = workflowActionRepo.saveContext(workflowActionContext);

            //Если контекст уже существует - пропустить задачку
            if (!isContextSaved) return;
            executionBlock.start(workflowActionContext);
        }
    }
}
