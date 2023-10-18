package com.petra.lib.environment.query.task;

import com.petra.lib.block.BlockId;

public interface InputTask extends Runnable{

    BlockId getBlockId();
    boolean isSequentially();
}
