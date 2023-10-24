package com.petra.lib.block;

import com.petra.lib.environment.context.ActivityContext;


public interface Block {
    BlockId getId();
    String getName();

    void execute(ActivityContext activityContext);

    boolean isSequentially();
    void error();
}
