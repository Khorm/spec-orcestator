package com.petra.lib.action.new_action;

import com.petra.lib.action.new_action.repo.ActionRepo;
import com.petra.lib.action.new_action.user.UserExecutor;
import com.petra.lib.query.InputTask;
import com.petra.lib.query.ThreadQuery;
import com.petra.lib.remote.dto.BlockRequestDto;
import com.petra.lib.remote.enums.BlockRequestResult;

public class Action {

    Long actionId;
    ActionRepo actionRepo;

    Long callingSignalId;

    ThreadQuery threadQuery;

    UserExecutor userExecutor;
    public BlockRequestResult execute(BlockRequestDto blockRequestDto){
        ActionContext actionContext = new ActionContext(
                actionId,
                blockRequestDto.getScenarioId(),
                blockRequestDto.getBlockVariables(),
                blockRequestDto.getProducerId(),
                blockRequestDto.getRequestWorkflowServiceName());
        actionContext.setNewState(ActionState.STARTED);
        boolean isContextSaved = actionRepo.saveContext(actionContext);
        if (!isContextSaved) return BlockRequestResult.REPEAT;

        threadQuery.popInQueue(new InputTask() {
            @Override
            public void run() {
                try {
                    userExecutor.execute(actionContext);

                } catch (Exception e) {

                }
            }
        });



    }
}
