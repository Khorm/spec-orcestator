package com.petra.lib.workflow.repo;

import com.petra.lib.action.BlockState;
import com.petra.lib.variable.value.ValuesContainer;
import com.petra.lib.workflow.context.WorkflowContext;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

public interface WorkflowRepo {

    Optional<WorkflowContext> findContext(UUID scenarioId, Long workflowId);

    boolean createContext(WorkflowContext workflowContext);

    boolean updateStateToExecuted(WorkflowContext workflowContext);
    void updateValues(UUID scenarioId, Long workflowId, ValuesContainer valuesContainer);
    boolean updateStateToComplete(WorkflowContext workflowContext, BlockState workflowState);
    boolean updateStateToError(WorkflowContext workflowContext, BlockState workflowState);

    Collection<WorkflowContext> findAllNotEndedContextsByWorkflowId(Long workflowId);


}
