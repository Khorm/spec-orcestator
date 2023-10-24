package com.petra.lib.block.gearbox;

import com.petra.lib.environment.context.ActivityContext;
import com.petra.lib.state.ActionState;

public interface Gearbox {
    ActionState getNextHandler(ActivityContext activityContext);
}
