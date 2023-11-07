package com.petra.lib.state.variable.loaders;

import com.petra.lib.context.ActivityContext;

/**
 * ��������� ��� ����������
 */
public interface VariableLoader {

    /**
     * ���������� �������� ������
     * @param activityContext
     */
    void handle(ActivityContext activityContext);
    boolean isReady(ActivityContext activityContext);
}
