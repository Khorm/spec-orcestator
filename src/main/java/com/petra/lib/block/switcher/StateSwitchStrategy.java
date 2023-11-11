package com.petra.lib.block.switcher;

import com.petra.lib.context.ActivityContext;
import com.petra.lib.state.ActionState;

public interface StateSwitchStrategy {
    ActionState getNextHandler(ActivityContext activityContext);
}
