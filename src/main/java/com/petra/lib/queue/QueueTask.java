package com.petra.lib.queue;

import com.petra.lib.manager.block.Block;

import java.util.UUID;

public interface QueueTask extends Runnable{
    void setBlock(Block block);
    Long getTaskBlockId();
}
