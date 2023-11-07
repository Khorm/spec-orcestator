package com.petra.lib.block.switcher;

import com.petra.lib.context.ActivityContext;
import com.petra.lib.context.state.ActionState;

public interface StateSwitchSequence {
    ActionState getNextHandler(ActivityContext activityContext);
}
