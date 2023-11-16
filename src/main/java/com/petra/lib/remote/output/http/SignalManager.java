package com.petra.lib.remote.output.http;

import com.petra.lib.context.ActivityContext;
import com.petra.lib.remote.dto.AnswerDto;

public interface SignalManager {
    /**
     * Отпроавить заранее созданный сигнал
     * @param activityContext
     * @return
     */
    AnswerDto send(ActivityContext activityContext);

    /**
     * обработать полученный на сигнал ответ
     * @param activityContext
     * @return
     */
//    VariablesContainer getAnswer(ActivityContext activityContext);
}
