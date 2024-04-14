package com.petra.lib.remote.controller;

import com.petra.lib.action.Action;
import com.petra.lib.remote.dto.BlockRequestDto;
import com.petra.lib.remote.dto.BlockResponseDto;
import com.petra.lib.remote.dto.SourceRequestDto;
import com.petra.lib.remote.dto.SourceResponseDto;
import com.petra.lib.remote.enums.BlockRequestResult;
import com.petra.lib.remote.enums.BlockResponseResult;
import com.petra.lib.source.Source;
import com.petra.lib.source.enums.SourceResultStatus;
import com.petra.lib.workflow.Workflow;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.Map;

/**
 * Обработчик сигналов. Подгатавливает сигнал к выполнению в блоке.
 */
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class InputExecutor {

    /**
     * репозиторий активити
     */
//    com.petra.lib.remote.repo.ActivityRepoImpl activityRepoImpl;

//    WorkflowActionRepo workflowActionRepo;

    /**
     * Список всех активностей
     */
    Map<Long, Action> actions;

    /**
     * Список всех воркфлоу
     */
    Map<Long, Workflow> workflows;

    /**
     * Список всех воркфлоу
     */
    Map<Long, Source> sources;

//    /**
//     * Имя текцщего сервиса
//     */
//    String currentServiceName;
//
//    /**
//     * Айди текущего сервиса
//     */
//    Long currentServiceId;


    BlockRequestResult execute(BlockRequestDto inputSignal) {
        try {
            Action action = actions.get(inputSignal.getConsumerId());
            if (action != null) {
                return action.execute(inputSignal);
            }

            Workflow workflow = workflows.get(inputSignal.getConsumerId());
            if (workflow != null) {
                return workflow.execute(inputSignal);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return BlockRequestResult.ERROR;
    }

    BlockResponseResult handleAnswer(BlockResponseDto responseDto) {
        try {
            Workflow workflow = workflows.get(responseDto.getProducerBlockId());
            return workflow.blockExecuted(responseDto);
        } catch (Exception e) {
            e.printStackTrace();
            return BlockResponseResult.ERROR;
        }

    }


    SourceResponseDto handleSource(SourceRequestDto sourceRequestDto) {
        try {
            Source source = sources.get(sourceRequestDto.getSourceId());
            return source.execute(sourceRequestDto);
        } catch (Exception e) {
            return new SourceResponseDto(null, null, null, SourceResultStatus.ERROR);
        }
    }

//    private ResponseDto executeBlock(Block execitingBlock, RequestDto inputSignal){
//        Optional<ActionContext> actionOpt =
//                activityRepoImpl.getActionContext(inputSignal.getScenarioId(), execitingBlock.getId());
//        if (actionOpt.isPresent()) {
//            return RequestResult.REPEAT;
//        } else {
//            ActionContext newActionContext = activityRepoImpl.createNewActionContext(inputSignal, execitingBlock, currentServiceName, currentServiceId);
//            boolean isStarted = threadQuery.pop(new ActionInputTask(execitingBlock, newActionContext));
//            if (!isStarted) {
//                return RequestResult.OVERLOAD;
//            }
//
//        }
//        return RequestResult.OK;
//    }
//
//    private ResponseDto executeWorkflow(Workflow workflow, RequestDto inputSignal){
//        Optional<WorkflowActionContext> workflowContextOptional =
//                workflowActionRepo.findContext(inputSignal.getScenarioId(), workflow.getId());
//        if (workflowContextOptional.isPresent()) {
//            return RequestResult.REPEAT;
//        } else {
//            WorkflowActionContext newWorkflowActionContext = workflowActionRepo.createNewContext();
//            boolean isStarted = threadQuery.pop(new WorkflowInputTask(newWorkflowActionContext, workflow));
//            if (!isStarted) {
//                return RequestResult.OVERLOAD;
//            }
//        }
//        return RequestResult.OK;
//    }


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
