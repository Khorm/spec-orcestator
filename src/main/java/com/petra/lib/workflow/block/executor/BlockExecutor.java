package com.petra.lib.workflow.block.executor;

import com.petra.lib.remote.enums.BlockRequestResult;
import com.petra.lib.workflow.context.WorkflowActionContext;

public interface BlockExecutor {
    BlockRequestResult startExecuting(WorkflowActionContext workflowActionContext);
}
