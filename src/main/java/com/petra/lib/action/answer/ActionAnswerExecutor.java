package com.petra.lib.action.answer;

import com.petra.lib.action.ActionContext;
import com.petra.lib.action.BlockState;
import com.petra.lib.action.repo.ActionRepo;
import com.petra.lib.remote.dto.BlockResponseDto;
import com.petra.lib.remote.enums.BlockRequestStatus;
import com.petra.lib.remote.enums.BlockResponseResult;
import com.petra.lib.remote.response.BlockResponse;
import com.petra.lib.remote.thread.RemoteThreadManager;

import java.util.concurrent.TimeUnit;

public class ActionAnswerExecutor {

    Long callingSignalId;
    BlockResponse blockResponse;

    RemoteThreadManager remoteThreadManager;

    ActionRepo actionRepo;

    public void execute(ActionContext actionContext, BlockRequestStatus blockRequestStatus){
        BlockResponseDto blockResponseDto = new BlockResponseDto(
                actionContext.getScenarioId(),
                actionContext.getActionId(),
                actionContext.getActionVariables(),
                blockRequestStatus,
                callingSignalId,
                actionContext.getRequestWorkflowId()
        );

        remoteThreadManager.handle(() -> {
            BlockResponseResult blockResponseResult;
            do{
                blockResponseResult = blockResponse.response(blockResponseDto, actionContext.getRequestServiceName());
                try {
                    TimeUnit.SECONDS.sleep(5);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }while (blockResponseResult == BlockResponseResult.ERROR);

            actionContext.setActionState(BlockState.COMPLETE);
            try {
                actionRepo.updateActionState(actionContext.getScenarioId(),
                        actionContext.getActionId(), actionContext.getActionState());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }
}
