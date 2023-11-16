package com.petra.lib.remote.output.http;

import com.petra.lib.context.ActivityContext;
import com.petra.lib.remote.dto.AnswerDto;

public interface SignalManager {
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
//    VariablesContainer getAnswer(ActivityContext activityContext);
}
