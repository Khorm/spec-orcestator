package com.petra.lib.action;

import com.petra.lib.action.answer.ActionAnswerExecutor;
import com.petra.lib.action.repo.ActionRepo;
import com.petra.lib.action.user.UserExecutor;
import com.petra.lib.query.ThreadQuery;
import com.petra.lib.remote.dto.BlockRequestDto;
import com.petra.lib.remote.enums.BlockRequestResult;
import com.petra.lib.remote.enums.BlockRequestStatus;

public class Action {

    Long actionId;
    ActionRepo actionRepo;
    ThreadQuery threadQuery;
    UserExecutor userExecutor;
    ActionAnswerExecutor actionAnswerExecutor;

    public BlockRequestResult execute(BlockRequestDto blockRequestDto) {
        ActionContext actionContext = new ActionContext(
                actionId,
                blockRequestDto.getScenarioId(),
                blockRequestDto.getBlockVariables(),
                blockRequestDto.getProducerId(),
                blockRequestDto.getRequestWorkflowServiceName());
        actionContext.setActionState(BlockState.EXECUTING);
        boolean isContextSaved = actionRepo.createContext(actionContext);
        if (!isContextSaved) return BlockRequestResult.REPEAT;

        threadQuery.popInQueue(() -> {
            try {
                userExecutor.execute(actionContext);
                actionAnswerExecutor.execute(actionContext, BlockRequestStatus.EXECUTED);
            } catch (Exception e) {
                e.printStackTrace();
                actionAnswerExecutor.execute(actionContext, BlockRequestStatus.ERROR);
            }
        });
        return BlockRequestResult.OK;
    }
}
