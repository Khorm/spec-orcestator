package com.petra.lib.workflow.response;

import com.petra.lib.action.BlockState;
import com.petra.lib.remote.dto.BlockResponseDto;
import com.petra.lib.remote.enums.BlockExecutionStatus;
import com.petra.lib.remote.enums.BlockResponseResult;
import com.petra.lib.remote.response.block.BlockResponse;
import com.petra.lib.remote.thread.RemoteThreadManager;
import com.petra.lib.variable.value.ValuesContainerFactory;
import com.petra.lib.workflow.context.WorkflowContext;
import com.petra.lib.workflow.repo.WorkflowRepo;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.concurrent.TimeUnit;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class WorkflowResponseExecutor {
    Long callingSignalId;
    BlockResponse blockResponse;
    RemoteThreadManager remoteThreadManager;
    WorkflowRepo workflowRepo;

    public void answer(WorkflowContext workflowContext, BlockExecutionStatus blockExecutionStatus) {
        BlockResponseDto blockResponseDto = new BlockResponseDto(
                workflowContext.getScenarioId(),
                workflowContext.getConsumerId(),
                ValuesContainerFactory.toJson(workflowContext.getResponseVariables()),
                blockExecutionStatus,
                callingSignalId,
                workflowContext.getProducerId()

        );

        remoteThreadManager.handle(() -> {
            BlockResponseResult blockResponseResult;
            do {
                blockResponseResult = blockResponse.response(blockResponseDto, workflowContext.getRequestServiceName());
                try {
                    TimeUnit.SECONDS.sleep(5);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            } while (blockResponseResult == BlockResponseResult.ERROR);
            if (blockExecutionStatus == BlockExecutionStatus.EXECUTED) {
                workflowContext.setWorkflowState(BlockState.COMPLETE);
                workflowRepo.updateStateToComplete(workflowContext, BlockState.COMPLETE);
            }else {
                workflowContext.setWorkflowState(BlockState.ERROR);
                workflowRepo.updateStateToComplete(workflowContext, BlockState.ERROR);
            }
        });
    }
}
