package com.petra.lib.workflow.block.source;

import com.petra.lib.remote.dto.SourceResponseDto;
import com.petra.lib.workflow.context.WorkflowActionContext;

class WorkflowBlockExecutor implements SourceHandler{
    @Override
    public boolean startValuesFilling(WorkflowActionContext workflowActionContext) {
        return true;
    }

    @Override
    public boolean setSourceAnswer(WorkflowActionContext workflowActionContext, SourceResponseDto sourceResponseModel) {
        return true;
    }
}
