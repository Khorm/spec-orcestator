package com.petra.lib.workflow.block;

import com.petra.lib.remote.request.block.BlockRequest;
import com.petra.lib.remote.request.source.SourceRequest;
import com.petra.lib.variable.pure.PureVariableList;
import com.petra.lib.workflow.block.executor.BlockExecutor;
import com.petra.lib.workflow.block.executor.BlockExecutorFactory;
import com.petra.lib.workflow.block.source.SourceHandler;
import com.petra.lib.workflow.block.source.SourceHandlerFactory;
import com.petra.lib.workflow.repo.WorkflowActionRepo;
import com.petra.lib.workflow.repo.WorkflowRepo;
import com.petra.lib.workflow.signal.SignalManager;
import com.petra.lib.workflow.subscribe.SubscribeManager;

public final class ActionBlockFactory {

    public static ActionBlock createActionBlock(BlockBuilderModel blockBuilderModel,
                                                WorkflowActionRepo workflowActionRepo,
                                                SubscribeManager subscribeManager,
                                                SignalManager signalManager,
                                                WorkflowRepo workflowRepo,
                                                SourceRequest sourceRequest,
                                                BlockRequest blockRequest){

        PureVariableList blockVariableList = new PureVariableList(blockBuilderModel.getBlockVariables());

        SourceHandler sourceHandler = null;
        if (blockBuilderModel.getBlockType() == BlockType.ACTION){
            sourceHandler = SourceHandlerFactory.createActionSource(
                    blockBuilderModel.getSourceHandlerBuildModel(),
                    sourceRequest,
                    blockVariableList,
                    workflowActionRepo
            );
        }else if (blockBuilderModel.getBlockType() == BlockType.WORKFLOW){
            sourceHandler = SourceHandlerFactory.createWorkflowSource();
        }

        BlockExecutor blockExecutor = BlockExecutorFactory.createBlockExecutor(blockBuilderModel.getBlockExecutorBuildModel(),
                blockRequest);

        return new ActionBlock(blockBuilderModel.getBlockId(),
                workflowActionRepo,
                sourceHandler,
                blockExecutor,
                blockBuilderModel.getListeningSignals(),
                subscribeManager,
                signalManager,
                workflowRepo,
                blockBuilderModel.isLastBlock(),
                blockBuilderModel.isWorkflowContextBlock());
    }


}
