package com.petra.lib.workflow.block;

import com.petra.lib.remote.dto.BlockRequestDto;
import com.petra.lib.remote.enums.BlockRequestResult;
import com.petra.lib.remote.request.block.BlockRequest;
import com.petra.lib.workflow.context.WorkflowActionContext;
import com.petra.lib.workflow.enums.WorkflowActionState;
import com.petra.lib.workflow.repo.WorkflowActionRepo;
import com.petra.lib.workflow.variables.LoadVariablesManager;

/**
 * Класс отвечает за запуск активности из воркфлоу
 */
class ActionBlock implements ExecutionBlock {

    Long actionId;
    LoadVariablesManager loadVariablesManager;

    BlockRequest blockRequest;

    String producerServiceName;
    String consumerServiceName;

    WorkflowActionRepo workflowActionRepo;

    @Override
    public void start(WorkflowActionContext workflowActionContext) {
        try {
            loadVariablesManager.start(workflowActionContext);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        BlockRequestDto blockRequestDto = new BlockRequestDto(
                workflowActionContext.getScenarioId(),
                workflowActionContext.getWorkflowId(),
                actionId,
                workflowActionContext.getCurrentSignalId(),
                workflowActionContext.getBlockVariables(),
                producerServiceName
        );
        BlockRequestResult requestResult = blockRequest.requestBlockExec(blockRequestDto,consumerServiceName );
        if (requestResult == BlockRequestResult.REPEAT) return;

        workflowActionContext.setWorkflowState(WorkflowActionState.EXECUTING);
        workflowActionRepo.updateContext(workflowActionContext);

    }

    @Override
    public Long getId() {
        return actionId;
    }
}
