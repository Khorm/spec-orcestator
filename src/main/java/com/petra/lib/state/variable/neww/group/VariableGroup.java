package com.petra.lib.state.variable.neww.group;

import com.petra.lib.context.ActivityContext;

public interface VariableGroup {

    boolean isReady(ActivityContext activityContext);
    void fill(ActivityContext activityContext);
}
