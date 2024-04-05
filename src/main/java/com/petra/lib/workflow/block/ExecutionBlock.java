package com.petra.lib.workflow.block;

import com.petra.lib.workflow.context.WorkflowActionContext;

public interface ExecutionBlock {
    void start(WorkflowActionContext workflowActionContext);
    Long getId();
}
