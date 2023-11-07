package com.petra.lib.state.variable.loaders;

import com.petra.lib.context.ActivityContext;

/**
 * Загрузчик для переменных
 */
public interface VariableLoader {

    /**
     * Обработать входящий сигнал
     * @param activityContext
     */
    void handle(ActivityContext activityContext);
    boolean isReady(ActivityContext activityContext);
}
