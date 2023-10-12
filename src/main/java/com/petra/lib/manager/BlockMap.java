package com.petra.lib.manager;

import com.petra.lib.block.Block;
import com.petra.lib.signal.SignalId;

import java.util.Collection;

/**
 * Map contains all blocks in the service.
 * It includes action, service and workflow blocks.
 *
 */
public interface BlockMap {

    /**
     * Get block by ID
     * @param blockId block id
     * @return finding block
     */
    Block getById(Long blockId);

    /**
     * Get block by signal id, which its subscribed on.
     * @param signalId required signal id
     * @return Collection of blocks, subscribed on the signal
     */
    Collection<Block> getBySignalId(SignalId signalId);
}
