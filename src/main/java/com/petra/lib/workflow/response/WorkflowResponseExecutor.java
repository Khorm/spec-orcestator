package com.petra.lib.workflow.response;

import com.petra.lib.action.BlockState;
import com.petra.lib.remote.dto.BlockResponseDto;
import com.petra.lib.remote.enums.BlockRequestStatus;
import com.petra.lib.remote.enums.BlockResponseResult;
import com.petra.lib.remote.response.BlockResponse;
import com.petra.lib.remote.thread.RemoteThreadManager;
import com.petra.lib.workflow.context.WorkflowContext;
import com.petra.lib.workflow.repo.WorkflowRepo;

import java.util.concurrent.TimeUnit;

public class WorkflowResponseExecutor {
    Long callingSignalId;
    BlockResponse blockResponse;

    RemoteThreadManager remoteThreadManager;

    WorkflowRepo workflowRepo;

    public void answer(WorkflowContext workflowContext, BlockRequestStatus blockRequestStatus){
        BlockResponseDto blockResponseDto = new BlockResponseDto(
                workflowContext.getScenarioId(),
                workflowContext.getConsumerId(),
                workflowContext.getResponseVariables(),
                blockRequestStatus,
                callingSignalId,
                workflowContext.getProducerId()

        );

        remoteThreadManager.handle(() -> {
            BlockResponseResult blockResponseResult;
            do{
                blockResponseResult = blockResponse.response(blockResponseDto, workflowContext.getRequestServiceName());
                try {
                    TimeUnit.SECONDS.sleep(5);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }while (blockResponseResult == BlockResponseResult.ERROR);

            workflowContext.setWorkflowState(BlockState.COMPLETE);
            workflowRepo.updateContext(workflowContext);
        });
    }
}
