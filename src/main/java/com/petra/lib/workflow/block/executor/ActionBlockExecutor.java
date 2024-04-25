package com.petra.lib.workflow.block.executor;

import com.petra.lib.remote.dto.BlockRequestDto;
import com.petra.lib.remote.enums.BlockRequestResult;
import com.petra.lib.remote.request.block.BlockRequest;
import com.petra.lib.variable.value.ValuesContainerFactory;
import com.petra.lib.workflow.context.WorkflowActionContext;
import com.petra.lib.workflow.repo.WorkflowActionRepo;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
class ActionBlockExecutor implements BlockExecutor{

    BlockRequest blockRequest;
    String producerServiceName;
    String consumerServiceName;

    @Override
    public BlockRequestResult startExecuting(WorkflowActionContext workflowActionContext) {
        BlockRequestDto blockRequestDto = new BlockRequestDto(
                workflowActionContext.getScenarioId(),
                workflowActionContext.getWorkflowId(),
                workflowActionContext.getExecBlock(),
                workflowActionContext.getCallingSignalId(),
                ValuesContainerFactory.toJson(workflowActionContext.getBlockVariables()),
                producerServiceName
        );

        return blockRequest.requestBlockExec(blockRequestDto,consumerServiceName );
    }

}
