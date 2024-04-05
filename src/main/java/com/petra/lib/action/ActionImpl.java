package com.petra.lib.action;

import com.petra.lib.action.new_action.ActionState;
import com.petra.lib.action.handler.error.ErrorState;
import com.petra.lib.action.handler.init.ActionInitializer;
import com.petra.lib.action.handler.response.ResponseState;
import com.petra.lib.action.new_action.ActionContext;
import com.petra.lib.action.new_action.user.UserExecutor;
import com.petra.lib.action.new_action.repo.ActionRepo;
import com.petra.lib.block.Block;
import com.petra.lib.block.BlockId;
import com.petra.lib.variable.value.VariableContainerFactory;
import com.petra.lib.query.ThreadQuery;
import com.petra.lib.remote.request.RequestDto;
import com.petra.lib.remote.response.ResponseDto;
import com.petra.lib.remote.response.ResponseSignalType;
import com.petra.lib.transaction.TransactionManager;
import lombok.extern.log4j.Log4j2;

import java.util.Optional;

@Log4j2
public class ActionImpl implements Block {

    ActionRepo actionRepo;

    ActionInitializer initializer;
    UserExecutor userExecutor;
    ResponseState responseState;
    ErrorState errorState;


    BlockId actionId;

    ThreadQuery threadQuery;

    TransactionManager transactionManager;

    @Override
    public BlockId getId() {
        return null;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public ResponseDto execute(RequestDto requestDto) {
        try {
            //проверка на повторный вызов
            Optional<ActionContext> contextOptional = actionRepo.findActionContext(requestDto.getScenarioId(), actionId);
            if (contextOptional.isPresent()) {
                ActionContext actionContext = contextOptional.get();
                if (actionContext.getState() == ActionState.COMPLETE) {
                    return createResponseDto(requestDto, ResponseSignalType.REQUEST_REPEAT);
                } else if (actionContext.getState() == ActionState.ERROR) {
                    return createResponseDto(requestDto, ResponseSignalType.ACTION_ERROR);
                }else {
                    return createResponseDto(requestDto, ResponseSignalType.EXECUTING);
                }
            }

            ActionContext actionContext = createContext(requestDto);

            boolean saveResult = transactionManager.executeInTransaction(jpaTransactionManager -> {
                return actionRepo.saveContext(actionContext);
            });
            if (!saveResult) {
                log.debug("Execution crossing. Stop execution {}", actionContext.getScenarioId());
                return createResponseDto(requestDto, ResponseSignalType.REQUEST_ACCEPTED);
            }

            boolean popResult = startActionExecution(actionContext);

            if (popResult) {
                return createResponseDto(requestDto, ResponseSignalType.REQUEST_ACCEPTED);
            } else {
                return createResponseDto(requestDto, ResponseSignalType.OVERLOAD);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return createResponseDto(requestDto, ResponseSignalType.ACTION_ERROR);
        }
    }

    private ResponseDto createResponseDto(RequestDto requestDto, ResponseSignalType responseSignalType) {
        return new ResponseDto(
                requestDto.getScenarioId(),
                actionId,
                null,
                actionId,
                responseSignalType,
                requestDto.getRequestBlockId()
        );
    }


    private ActionContext createContext(RequestDto requestDto) {
        ActionContext actionContext = new ActionContext(
                actionId,
                requestDto.getScenarioId(),
                VariableContainerFactory.getSimpleVariableContainer(),
                requestDto.getRequestBlockId(),
                requestDto.getRequestWorkflowServiceName(),
                requestDto.getSignalVariables());
        actionContext.setNewState(ActionState.STARTED);
        return actionContext;
    }

    private boolean startActionExecution(ActionContext actionContext){
        return threadQuery.forcedPop(() -> {
            try {
                boolean initResult =  initializer.initialize(actionContext);
                if (!initResult) return;
                boolean executeResult = userExecutor.execute(actionContext);
                if (!executeResult) return;
                responseState.execute(actionContext);
            }catch (Exception e){
                errorState.execute(actionContext);
            }
        });
    }
}
