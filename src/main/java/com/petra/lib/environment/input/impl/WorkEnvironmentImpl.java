package com.petra.lib.environment.input.impl;

import com.petra.lib.block.Block;
import com.petra.lib.block.BlockId;
import com.petra.lib.environment.context.ActivityContext;
import com.petra.lib.environment.dto.AnswerDto;
import com.petra.lib.environment.dto.Signal;
import com.petra.lib.environment.input.WorkEnvironment;
import com.petra.lib.environment.output.enums.SignalResult;
import com.petra.lib.environment.query.ThreadQuery;
import com.petra.lib.environment.query.task.ActionInputTask;
import com.petra.lib.environment.repo.ActionRepo;
import com.petra.lib.state.ActionState;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
class WorkEnvironmentImpl implements WorkEnvironment {

    ActionRepo actionRepo;
    Map<BlockId, Block> blocks;
    ThreadQuery threadQuery;
    String serviceName;


    @Override
    public AnswerDto consume(Signal inputSignal) {
        try {
            //блок которому пришел сигнал
            Block blockForAction = blocks.get(inputSignal.getConsumerBlockId());
            Optional<ActivityContext> actionOpt =
                    actionRepo.getCurrentScenarioAction(inputSignal.getScenarioId(), inputSignal.getConsumerBlockId());

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

    private void createInitTask(Block blockForAction, Signal inputSignal) {
        if (blockForAction == null) throw new IllegalArgumentException("Block not found");
        ActivityContext newActivityContext = actionRepo.createScenarioModel(inputSignal.getScenarioId(),
                blockForAction.getId(), serviceName);
        newActivityContext.setCurrentState(ActionState.INITIALIZING);

        ActionInputTask initTask = new ActionInputTask(blockForAction,
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
