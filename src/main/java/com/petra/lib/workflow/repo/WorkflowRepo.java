package com.petra.lib.workflow.repo;

import com.petra.lib.exception.RepeatedExecutionException;
import com.petra.lib.workflow.context.WorkflowContext;

import java.util.Optional;
import java.util.UUID;

public interface WorkflowRepo {

    Optional<WorkflowContext> findContext(UUID scenarioId, Long workflowId);

    boolean createContext(WorkflowContext workflowContext);
    boolean updateContext(WorkflowContext workflowContext);


}
