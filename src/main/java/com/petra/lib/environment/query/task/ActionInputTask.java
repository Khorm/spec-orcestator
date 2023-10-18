package com.petra.lib.environment.query.task;

import com.petra.lib.XXXXXcontext.DirtyVariablesList;
import com.petra.lib.block.Block;
import com.petra.lib.block.BlockId;
import com.petra.lib.environment.model.ScenarioContext;
import com.petra.lib.variable.mapper.VariableMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

/**
 * Начинает активность с начала с пустым контектом
 */
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ActionInputTask implements InputTask {

    Block actionBlock;
    ScenarioContext scenarioContext;
    DirtyVariablesList signalVariableList;

    @Override
    public void run() {
        //добавить переменные из сигнала
        DirtyVariablesList dirtyVariablesList = signalToActionVariableMapper.map(signalVariableList);
        scenarioContext.syncCurrentInputVariableList(dirtyVariablesList);

        actionBlock.execute(scenarioContext);
    }


    @Override
    public BlockId getBlockId() {
        return actionBlock.getId();
    }

    @Override
    public boolean isSequentially() {
        return actionBlock.isSequentially();
    }
}
