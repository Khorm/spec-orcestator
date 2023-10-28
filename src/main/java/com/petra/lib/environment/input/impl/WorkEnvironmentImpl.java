package com.petra.lib.environment.input.impl;

import com.petra.lib.block.Block;
import com.petra.lib.block.VersionBlockId;
import com.petra.lib.context.ActivityContext;
import com.petra.lib.environment.dto.AnswerDto;
import com.petra.lib.environment.dto.Signal;
import com.petra.lib.environment.input.WorkEnvironment;
import com.petra.lib.environment.output.enums.SignalResult;
import com.petra.lib.environment.query.ThreadQuery;
import com.petra.lib.environment.query.task.ActionInputTask;
import com.petra.lib.context.repo.ActionRepo;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.Map;
import java.util.Optional;

/**
 * Обработчик сигналов. ПОдгатавливает сигнал к выполнению в блоке.
 */
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
class WorkEnvironmentImpl implements WorkEnvironment {


    ActionRepo actionRepo;

    /**
     * Список всех блоков
     */
    Map<VersionBlockId, Block> blocks;

    /**
     * Очередь потоков для блоков
     */
    ThreadQuery threadQuery;

    /**
     * Имя сервиса
     */
    String serviceName;


    @Override
    public AnswerDto consume(Signal inputSignal) {
        try {
            //блок которому пришел сигнал
            Block blockForAction = blocks.get(inputSignal.getConsumerBlockId());
            Optional<ActivityContext> actionOpt =
                    actionRepo.getActionContext(inputSignal.getScenarioId(), inputSignal.getConsumerBlockId());

            //если сигнал раньше не приходил и происходит ининциализация
            if (actionOpt.isEmpty()) {
                createInitTask(blockForAction, inputSignal);
            } else {
                createContinueSignal(actionOpt.get(), blockForAction, inputSignal);
            }
            return new AnswerDto(SignalResult.OK);
        } catch (Exception e) {
            return new AnswerDto(SignalResult.ERROR);
        }
    }

    private void createInitTask(Block actionBlock, Signal inputSignal) {
        if (actionBlock == null) throw new IllegalArgumentException("Block not found");
        ActivityContext newActivityContext = actionRepo.createNewActionContext(inputSignal);

        ActionInputTask initTask = new ActionInputTask(actionBlock,
                newActivityContext,
                inputSignal.getVariablesContext());
        threadQuery.pop(initTask);
    }


    private void createContinueSignal(ActivityContext loadActivityContext, Block blockForAction, Signal inputSignal) {
        threadQuery.pop(new ActionInputTask(
                blockForAction,
                loadActivityContext,
                inputSignal.getVariablesContext()
        ));
    }
}
