package com.petra.lib.environment.query.task;

import com.petra.lib.block.VersionBlockId;

public interface InputTask extends Runnable{

    VersionBlockId getBlockId();
    boolean isSequentially();
}
