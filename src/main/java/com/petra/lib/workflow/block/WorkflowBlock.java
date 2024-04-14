package com.petra.lib.workflow.block;

import com.petra.lib.remote.dto.BlockRequestDto;
import com.petra.lib.remote.enums.BlockRequestResult;
import com.petra.lib.remote.request.block.BlockRequest;
import com.petra.lib.workflow.context.WorkflowActionContext;
import com.petra.lib.workflow.enums.WorkflowActionState;
import com.petra.lib.workflow.repo.WorkflowActionRepo;

public class WorkflowBlock implements ExecutionBlock{

    Long consumerWorkflowId;
    String producerServiceName;
    BlockRequest blockRequest;
    WorkflowActionRepo workflowActionRepo;

    String requestWorkflowServiceName;
    @Override
    public void start(WorkflowActionContext workflowActionContext) {
        BlockRequestDto blockRequestDto = new BlockRequestDto(
                workflowActionContext.getScenarioId(),
                consumerWorkflowId,
                workflowActionContext.getExecBlock(),
                workflowActionContext.getCurrentSignalId(),
                //воркфлоу передается нераспарсеные переменные сигнала
                workflowActionContext.getSignalVariables(),
                producerServiceName
        );
        BlockRequestResult requestResult = blockRequest.requestBlockExec(blockRequestDto, requestWorkflowServiceName);
        if (requestResult == BlockRequestResult.REPEAT) return;
        workflowActionContext.setWorkflowState(WorkflowActionState.EXECUTING);
        workflowActionRepo.updateContext(workflowActionContext);
    }

    @Override
    public Long getId() {
        return consumerWorkflowId;
    }
}
