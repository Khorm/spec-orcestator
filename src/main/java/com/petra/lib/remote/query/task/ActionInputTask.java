package com.petra.lib.remote.query.task;

import com.petra.lib.block.Block;
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

    /**
     * Исполняемый задачей блок
     */
    Block actionBlock;

    /**
     * Ияполняемый конектст
     */
    ActivityContext activityContext;

    @Override
    public void run() {
        actionBlock.execute(activityContext);
    }

}
