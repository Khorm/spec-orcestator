package com.petra.lib.environment.input.impl;

import com.petra.lib.block.Block;
import com.petra.lib.block.BlockId;
import com.petra.lib.environment.dto.Signal;
import com.petra.lib.environment.input.WorkEnvironment;
import com.petra.lib.environment.model.ScenarioContext;
import com.petra.lib.environment.query.ThreadQuery;
import com.petra.lib.environment.query.task.ActionInputTask;
import com.petra.lib.environment.repo.ActionRepo;
import com.petra.lib.XXXXXXsignal.SignalId;
import com.petra.lib.state.State;
import com.petra.lib.variable.mapper.VariableMapper;
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

    /**
     * мапперы для всехсигналов которые огу прийти блокам
     */
    Map<SignalId, Map<BlockId, VariableMapper>> signalMappers;

    @Override
    public void consume(Signal inputSignal) {
        //блок которому пришел сигнал
        Block blockForAction = blocks.get(inputSignal.getConsumerBlockId());
        Optional<ScenarioContext> actionOpt =
                actionRepo.getCurrentScenarioAction(inputSignal.getScenarioId(), inputSignal.getConsumerBlockId());

        //если сигнал раньше не приходил и происходит ининциализация
        if (actionOpt.isEmpty()) {
            createInitTask(blockForAction, inputSignal);
        } else {
            createContinueSignal(actionOpt.get(), blockForAction, inputSignal);
        }
    }

    private void createInitTask(Block blockForAction, Signal inputSignal) {
        if (blockForAction == null) throw new IllegalArgumentException("Block not found");
        ScenarioContext newScenarioContext = actionRepo.createScenarioModel(inputSignal.getScenarioId(),
                blockForAction.getId(), serviceName);
        newScenarioContext.setCurrentState(State.INITIALIZING);

        ActionInputTask initTask = new ActionInputTask(blockForAction,
                newScenarioContext,
                signalMappers.get(inputSignal.getSignalId()).get(blockForAction.getId()),
                inputSignal.getDirtyVariablesList());
        threadQuery.pop(initTask);
    }


    private void createContinueSignal(ScenarioContext loadScenarioContext, Block blockForAction, Signal inputSignal){
        threadQuery.pop(new ActionInputTask(
                blockForAction,
                loadScenarioContext,
                signalMappers.get(inputSignal.getSignalId()).get(blockForAction.getId()),
                inputSignal.getDirtyVariablesList()
        ));
    }
}
