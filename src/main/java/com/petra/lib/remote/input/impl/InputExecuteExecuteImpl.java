package com.petra.lib.remote.input.impl;

import com.petra.lib.block.Block;
import com.petra.lib.block.BlockVersionId;
import com.petra.lib.context.ActivityContext;
import com.petra.lib.remote.input.InputAnswerController;
import com.petra.lib.remote.input.InputExecuteController;
import com.petra.lib.remote.input.SignalResult;
import com.petra.lib.remote.query.ThreadQuery;
import com.petra.lib.remote.query.task.ActionInputTask;
import com.petra.lib.remote.repo.ActivityRepo;
import com.petra.lib.remote.signal.Signal;
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
class InputExecuteExecuteImpl implements InputExecuteController, InputAnswerController {

    /**
     * репозиторий активити
     */
    ActivityRepo activityRepo;

    /**
     * Список всех блоков
     */
    Map<BlockVersionId, Block> workingBlocks;

    /**
     * Очередь потоков для блоков
     */
    ThreadQuery threadQuery;

    /**
     * Имя текцщего сервиса
     */
    String currentServiceName;

    /**
     * Айди текущего сервиса
     */
    Long currentServiceId;

    @Override
    public SignalResult execute(Signal inputSignal) {
        Block execitingBlock = workingBlocks.get(inputSignal.getConsumerBlockId());
        Optional<ActivityContext> actionOpt =
                activityRepo.getActionContext(inputSignal.getScenarioId(), execitingBlock.getId());
        if (actionOpt.isPresent()) {
            return SignalResult.REPEAT;
        } else {
            ActivityContext newActivityContext = activityRepo.createNewActionContext(inputSignal, execitingBlock, currentServiceName, currentServiceId);
            boolean isStarted = threadQuery.pop(new ActionInputTask(execitingBlock, newActivityContext));
            if (!isStarted) {
                return SignalResult.OVERLOAD;
            }

        }
        return SignalResult.OK;
    }

    @Override
    public SignalResult answer(Signal signal) {
        Block blockForAnswer = workingBlocks.get(signal.getConsumerBlockId());
        Optional<ActivityContext> actionOpt =
                activityRepo.getActionContext(signal.getScenarioId(), blockForAnswer.getId());
        if (actionOpt.isPresent()){
            ActivityContext actionContext = actionOpt.get();
            if (actionContext.getState().canBeAnswered()){
                ActivityContext newActivityContext = activityRepo.createNewActionContext(signal, blockForAnswer, currentServiceName, currentServiceId);
                boolean isStarted = threadQuery.pop(new ActionInputTask(blockForAnswer, newActivityContext));
                if (!isStarted) {
                    return SignalResult.OVERLOAD;
                }
            }else {
                return SignalResult.ERROR;
            }
        }

        return SignalResult.OK;
    }

}
