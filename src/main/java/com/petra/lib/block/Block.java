package com.petra.lib.block;

import com.petra.lib.context.ActivityContext;


public interface Block {
    VersionBlockId getId();
    String getName();

    void execute(ActivityContext activityContext);

    boolean isSequentially();
    void error();
}
