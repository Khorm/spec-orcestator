package com.petra.lib.workflow.graph;

import com.petra.lib.block.ExecutingBlock;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Getter
public class BlockNode {
    ExecutingBlock block;
    List<BlockNode> parents;
    boolean entryPoint;
}
