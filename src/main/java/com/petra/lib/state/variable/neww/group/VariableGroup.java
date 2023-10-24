package com.petra.lib.state.variable.neww.group;

import com.petra.lib.environment.context.ActivityContext;
import com.petra.lib.environment.dto.Signal;

public interface VariableGroup {

    boolean isReady(ActivityContext activityContext);
    void fill(ActivityContext activityContext);
}
