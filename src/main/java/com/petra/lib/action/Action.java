package com.petra.lib.action;

import com.petra.lib.action.answer.ActionAnswerExecutor;
import com.petra.lib.action.repo.ActionRepo;
import com.petra.lib.action.user.UserExecutor;
import com.petra.lib.query.ThreadQuery;
import com.petra.lib.remote.dto.BlockRequestDto;
import com.petra.lib.remote.enums.BlockRequestResult;
import com.petra.lib.remote.enums.BlockExecutionStatus;
import com.petra.lib.variable.value.ValuesContainerFactory;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.Collection;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class Action {

    Long actionId;
    ActionRepo actionRepo;
    ThreadQuery threadQuery;
    UserExecutor userExecutor;
    ActionAnswerExecutor actionAnswerExecutor;

    public void start(){
        Collection<ActionContext> actionContexts = actionRepo.findNotCompletedContexts(actionId);
        for(ActionContext actionContext : actionContexts){
            if (actionContext.getActionState() == BlockState.EXECUTING){
                startExecuting(actionContext);
            }else if(actionContext.getActionState() == BlockState.EXECUTED){
                startExecuted(actionContext);
            }
        }
    }

    public BlockRequestResult execute(BlockRequestDto blockRequestDto) {
        ActionContext actionContext = new ActionContext(
                actionId,
                blockRequestDto.getScenarioId(),
                ValuesContainerFactory.fromJson(blockRequestDto.getBlockVariables()),
                blockRequestDto.getProducerId(),
                blockRequestDto.getRequestWorkflowServiceName());
        actionContext.setActionState(BlockState.EXECUTING);
        boolean isContextSaved = actionRepo.createContext(actionContext);
        if (!isContextSaved) return BlockRequestResult.REPEAT;
        startExecuted(actionContext);

        return BlockRequestResult.OK;
    }

    private void startExecuting(ActionContext actionContext){
        threadQuery.popInQueue(() -> {
            try {
                userExecutor.execute(actionContext);
                actionAnswerExecutor.execute(actionContext, BlockExecutionStatus.EXECUTED);
            } catch (Exception e) {
                e.printStackTrace();
                actionAnswerExecutor.execute(actionContext, BlockExecutionStatus.ERROR);
            }
        });
    }

    private void startExecuted(ActionContext actionContext){
        threadQuery.popInQueue(() -> {
            try {
                actionAnswerExecutor.execute(actionContext, BlockExecutionStatus.EXECUTED);
            } catch (Exception e) {
                e.printStackTrace();
                actionAnswerExecutor.execute(actionContext, BlockExecutionStatus.ERROR);
            }
        });
    }
}
