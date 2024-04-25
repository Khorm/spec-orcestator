package com.petra.lib.action.answer;

import com.petra.lib.action.ActionContext;
import com.petra.lib.action.BlockState;
import com.petra.lib.action.repo.ActionRepo;
import com.petra.lib.remote.dto.BlockResponseDto;
import com.petra.lib.remote.enums.BlockExecutionStatus;
import com.petra.lib.remote.enums.BlockResponseResult;
import com.petra.lib.remote.response.block.BlockResponse;
import com.petra.lib.remote.thread.RemoteThreadManager;
import com.petra.lib.variable.value.ValuesContainerFactory;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.concurrent.TimeUnit;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class ActionAnswerExecutor {

    Long callingSignalId;
    BlockResponse blockResponse;
    RemoteThreadManager remoteThreadManager;
    ActionRepo actionRepo;

    public void execute(ActionContext actionContext, BlockExecutionStatus blockExecutionStatus){
        BlockResponseDto blockResponseDto = new BlockResponseDto(
                actionContext.getScenarioId(),
                actionContext.getActionId(),
                ValuesContainerFactory.toJson(actionContext.getActionVariables()),
                blockExecutionStatus,
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
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        });
    }
}
