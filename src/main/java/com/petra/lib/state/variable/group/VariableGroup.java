package com.petra.lib.state.variable.group;

import com.petra.lib.context.ActivityContext;

public interface VariableGroup {

    /**
     * ��������� �� ������ ���������
     * @param activityContext
     * @return
     */
    boolean isReady(ActivityContext activityContext);
    void execute(ActivityContext activityContext);
    int groupNumber();
}
