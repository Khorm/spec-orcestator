package com.petra.lib.remote.output.manager;

import com.petra.lib.context.ActivityContext;
import com.petra.lib.context.value.VariablesContainer;
import com.petra.lib.remote.dto.AnswerDto;

public interface SignalRequestStrategy {
    /**
     * ���������� ������� ��������� ������
     * @param activityContext
     * @return
     */
    AnswerDto send(ActivityContext activityContext);

    /**
     * ���������� ���������� �� ������ �����
     * @param activityContext
     * @return
     */
    VariablesContainer getAnswer(ActivityContext activityContext);
}
