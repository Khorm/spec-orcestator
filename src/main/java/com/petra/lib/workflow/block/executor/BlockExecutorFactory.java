package com.petra.lib.workflow.block.executor;

import com.petra.lib.remote.request.block.BlockRequest;

public final class BlockExecutorFactory {
    public static BlockExecutor createBlockExecutor(BlockExecutorBuildModel blockExecutorBuildModel,
                                                    BlockRequest blockRequest){
        return new ActionBlockExecutor(blockRequest,
                blockExecutorBuildModel.getProducerServiceName(),
                blockExecutorBuildModel.getConsumerServiceName());
    }
}
