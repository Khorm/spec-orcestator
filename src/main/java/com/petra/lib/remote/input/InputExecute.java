package com.petra.lib.remote.input;

import com.petra.lib.block.Block;
import com.petra.lib.block.BlockId;
import com.petra.lib.action.new_action.ActionContext;
import com.petra.lib.remote.input.InputAnswerController;
import com.petra.lib.query.task.ActionInputTask;
import com.petra.lib.query.task.WorkflowAnswerTask;
import com.petra.lib.query.task.WorkflowInputTask;
import com.petra.lib.remote.request.RequestDto;
import com.petra.lib.remote.response.ResponseDto;
import com.petra.lib.workflow.Workflow;
import com.petra.lib.workflow.repo.WorkflowActionRepo;
import com.petra.lib.workflow.context.WorkflowActionContext;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.Map;
import java.util.Optional;

/**
 * Обработчик сигналов. Подгатавливает сигнал к выполнению в блоке.
 */
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public
class InputExecute {

    /**
     * репозиторий активити
     */
    com.petra.lib.remote.repo.ActivityRepoImpl activityRepoImpl;

    WorkflowActionRepo workflowActionRepo;

    /**
     * Список всех блоков
     */
    Map<BlockId, Block> workingBlocks;

    /**
     * Список всех воркфлоу
     */
    Map<BlockId, Workflow> workflows;

    /**
     * Имя текцщего сервиса
     */
    String currentServiceName;

    /**
     * Айди текущего сервиса
     */
    Long currentServiceId;


    public ResponseDto execute(RequestDto inputSignal) {
        Block execitingBlock = workingBlocks.get(inputSignal.getResponseBlockId());
        if (execitingBlock != null){
            return executeBlock(execitingBlock, inputSignal);
        }

        Workflow workflow = workflows.get(inputSignal.getResponseBlockId());
        if (workflow != null){
            return executeWorkflow(workflow, inputSignal);
        }
        return RequestResult.ERROR;
    }

    @Override
    public ResponseDto answer(ResponseDto responseDto) {
        Workflow workflow = workflows.get(responseDto.getRequestBlockId());
//        boolean isStarted = threadQuery.pop(new WorkflowAnswerTask(workflow, responseDto));
        workflow.answer(responseDto);
        return RequestResult.OK;
    }

    private ResponseDto executeBlock(Block execitingBlock, RequestDto inputSignal){
        Optional<ActionContext> actionOpt =
                activityRepoImpl.getActionContext(inputSignal.getScenarioId(), execitingBlock.getId());
        if (actionOpt.isPresent()) {
            return RequestResult.REPEAT;
        } else {
            ActionContext newActionContext = activityRepoImpl.createNewActionContext(inputSignal, execitingBlock, currentServiceName, currentServiceId);
            boolean isStarted = threadQuery.pop(new ActionInputTask(execitingBlock, newActionContext));
            if (!isStarted) {
                return RequestResult.OVERLOAD;
            }

        }
        return RequestResult.OK;
    }

    private ResponseDto executeWorkflow(Workflow workflow, RequestDto inputSignal){
        Optional<WorkflowActionContext> workflowContextOptional =
                workflowActionRepo.findContext(inputSignal.getScenarioId(), workflow.getId());
        if (workflowContextOptional.isPresent()) {
            return RequestResult.REPEAT;
        } else {
            WorkflowActionContext newWorkflowActionContext = workflowActionRepo.createNewContext();
            boolean isStarted = threadQuery.pop(new WorkflowInputTask(newWorkflowActionContext, workflow));
            if (!isStarted) {
                return RequestResult.OVERLOAD;
            }
        }
        return RequestResult.OK;
    }


//    public SignalResult getRequest(SignalDTO inputSignal){
//        Block execitingBlock = workingBlocks.get(inputSignal.getConsumerBlockId());
//        Optional<ActivityContext> actionOpt =
//                activityRepo.getActionContext(inputSignal.getScenarioId(), execitingBlock.getId());
//        if (actionOpt.isPresent()) {
//            return SignalResult.REPEAT;
//        } else {
//            ActivityContext newActivityContext = activityRepo.createNewActionContext(inputSignal, execitingBlock, currentServiceName, currentServiceId);
//            boolean isStarted = threadQuery.pop(new ActionInputTask(execitingBlock, newActivityContext));
//            if (!isStarted) {
//                return SignalResult.OVERLOAD;
//            }
//
//        }
//        return SignalResult.OK;
//    }


//    public SignalResult getAnswer(SignalDTO signal) {
//        Block blockForAnswer = workingBlocks.get(signal.getConsumerBlockId());
//        Optional<ActivityContext> actionOpt =
//                activityRepo.getActionContext(signal.getScenarioId(), blockForAnswer.getId());
//        if (actionOpt.isPresent()){
//            ActivityContext answeredActionContext = actionOpt.get();
//            if (answeredActionContext.getState().canBeAnswered()){
//                boolean isStarted = threadQuery.pop(new ActionInputTask(blockForAnswer, answeredActionContext));
//                if (!isStarted) {
//                    return SignalResult.OVERLOAD;
//                }
//            }else {
//                return SignalResult.ERROR;
//            }
//        }
//
//        return SignalResult.OK;
//    }

}
