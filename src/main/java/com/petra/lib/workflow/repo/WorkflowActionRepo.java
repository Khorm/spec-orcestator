package com.petra.lib.workflow.repo;

import com.petra.lib.variable.value.ValuesContainer;
import com.petra.lib.workflow.context.WorkflowActionContext;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

public interface WorkflowActionRepo {
    Optional<WorkflowActionContext> findContext(UUID scenarioId, Long workflowId, Long actionId);

    /**
     * Сохраняет несуществующий контекст
     * @param workflowActionContext
     * @return
     * true - контекст сохранен
     * false - контекст уже существует
     *
     */
    boolean createContext(WorkflowActionContext workflowActionContext);

    boolean updateStateToComplete(ValuesContainer blockVariables,
                                  Long nextSignalId, WorkflowActionContext workflowActionContext);

    boolean updateStateToExecuting(WorkflowActionContext workflowActionContext);
    boolean updateStateToError(WorkflowActionContext workflowActionContext);

    Collection<WorkflowActionContext> getScenarioContexts(UUID scenarioId, Long workflowID);

    void updateLoadingGroup(WorkflowActionContext context, int newGroup);

    void updateLoadSource(WorkflowActionContext context, ValuesContainer newValuesList, Long sourceId);



}
