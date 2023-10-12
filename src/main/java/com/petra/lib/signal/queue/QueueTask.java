package com.petra.lib.signal.queue;

import com.petra.lib.block.Block;

public interface QueueTask extends Runnable{
    void setBlock(Block block);
    Long getTaskBlockId();
}
