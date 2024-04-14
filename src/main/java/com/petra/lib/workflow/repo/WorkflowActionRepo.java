package com.petra.lib.workflow.repo;

import com.petra.lib.workflow.context.WorkflowActionContext;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

public interface WorkflowActionRepo {
    Optional<WorkflowActionContext> findContext(UUID scenarioId, Long workflowId, Long actionId);

//    void updateWorkflowContextState(UUID scenarioId, BlockId workflowId, BlockId actionId, WorkflowActionState workflowActionState);
//    void updateWorkflowContextStateAndOuterSignals(UUID scenarioId,
//                                                   BlockId workflowId,
//                                                   BlockId actionId,
//                                                   WorkflowActionState workflowActionState,
//                                                   Collection<ResponseSignal> responseSignals);

    /**
     * Сохраняет несуществующий контекст
     * @param workflowActionContext
     * @return
     * true - контекст сохранен
     * false - контекст уже существует
     *
     */
    boolean saveContext(WorkflowActionContext workflowActionContext);

    /**
     * обновляет контекст
     * @param workflowActionContext
     * @return
     * true - удалось обновить стейт контекста
     * false - не удалось обновить стейт контекста
     */
    boolean updateContext(WorkflowActionContext workflowActionContext);

    Collection<WorkflowActionContext> getScenarioContexts(UUID scenarioId, Long workflowID);

//    Collection<WorkflowActionContext> findExecutedWorkflows(UUID scenarioId, BlockId workflowId);
}
