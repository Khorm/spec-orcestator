package com.petra.lib.environment.query.task;

import com.petra.lib.context.variables.VariablesContext;
import com.petra.lib.block.Block;
import com.petra.lib.block.VersionBlockId;
import com.petra.lib.context.ActivityContext;
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
    ActivityContext activityContext;
    VariablesContext signalVariableList;

    @Override
    public void run() {
        //добавить переменные из сигнала
        VariablesContext variablesContext = signalToActionVariableMapper.map(signalVariableList);
        activityContext.syncCurrentInputVariableList(variablesContext);

        actionBlock.execute(activityContext);
    }


    @Override
    public VersionBlockId getBlockId() {
        return actionBlock.getId();
    }

    @Override
    public boolean isSequentially() {
        return actionBlock.isSequentially();
    }
}
