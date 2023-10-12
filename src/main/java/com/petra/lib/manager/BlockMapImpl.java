package com.petra.lib.manager;

import com.petra.lib.block.Block;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.Collection;
import java.util.Map;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Deprecated
public class BlockMapImpl implements BlockMap {
    Map<Long, Block> blocksBydId;
    Map<Long, Collection<Block>> blocksByListeningSignalId;


    @Override
    public Block getById(Long blockId) {
        return blocksBydId.get(blockId);
    }

    @Override
    public Collection<Block> getBySignalId(Long signalId) {
        return blocksByListeningSignalId.get(signalId);
    }
}
