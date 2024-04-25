package com.petra.lib.workflow.block;

import com.petra.lib.variable.pure.PureVariable;
import com.petra.lib.workflow.block.executor.BlockExecutorBuildModel;
import com.petra.lib.workflow.block.source.build.SourceHandlerBuildModel;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.util.Collection;
import java.util.Set;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
public class BlockBuilderModel {
    Long blockId;

    /**
     * Все переменные блока
     */
    Collection<PureVariable> blockVariables;
    boolean isLastBlock;
    boolean isWorkflowContextBlock;
    Set<Long> listeningSignals;
    BlockType blockType;
    SourceHandlerBuildModel sourceHandlerBuildModel;
    BlockExecutorBuildModel blockExecutorBuildModel;


}
