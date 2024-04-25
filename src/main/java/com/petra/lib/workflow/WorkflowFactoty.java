package com.petra.lib.workflow;

import com.petra.lib.remote.request.block.BlockRequest;
import com.petra.lib.remote.request.source.SourceRequest;
import com.petra.lib.remote.response.block.BlockResponse;
import com.petra.lib.remote.thread.RemoteThreadManager;
import com.petra.lib.transaction.TransactionManager;
import com.petra.lib.workflow.block.ActionBlock;
import com.petra.lib.workflow.block.ActionBlockFactory;
import com.petra.lib.workflow.block.BlockBuilderModel;
import com.petra.lib.workflow.repo.WorkflowActionRepo;
import com.petra.lib.workflow.repo.WorkflowRepo;
import com.petra.lib.workflow.repo.WorkflowRepoFactory;
import com.petra.lib.workflow.response.WorkflowResponseFactory;
import com.petra.lib.workflow.signal.SignalBuildModel;
import com.petra.lib.workflow.signal.SignalManager;
import com.petra.lib.workflow.signal.SignalManagerFactory;
import com.petra.lib.workflow.subscribe.SubscribeManager;

import java.util.ArrayList;
import java.util.Collection;

public class WorkflowFactoty {

    private static Workflow createWorkflow(WorkflowBuildModel workflowBuildModel,
                                          SignalManager signalManager,
                                          BlockResponse blockResponse,
                                          RemoteThreadManager remoteThreadManager,
                                          SubscribeManager subscribeManager,
                                          WorkflowActionRepo workflowActionRepo,
                                          WorkflowRepo workflowRepo) {
        return new Workflow(workflowBuildModel.getWorkflowId(),
                workflowRepo,
                signalManager,
                WorkflowResponseFactory.createWorkflowResponseExecutor(workflowBuildModel.getCallingSignalId(),
                        blockResponse,
                        remoteThreadManager,
                        workflowRepo),
                subscribeManager,
                workflowActionRepo,
                workflowBuildModel.getAllContainerBlockIds());
    }

    public static Collection<Workflow> createWorkflows(TransactionManager transactionManager,
                                                       Collection<SignalBuildModel> signalBuildModels,
                                                       BlockResponse blockResponse,
                                                       RemoteThreadManager remoteThreadManager,
                                                       Collection<WorkflowBuildModel> workflowBuildModels,
                                                       Collection<BlockBuilderModel> blockBuilderModels,
                                                       SourceRequest sourceRequest,
                                                       BlockRequest blockRequest) {
        WorkflowRepo workflowRepo = WorkflowRepoFactory.createWorkflowRepo(transactionManager);
        WorkflowActionRepo workflowActionRepo = WorkflowRepoFactory.createWorkflowActionRepo(transactionManager);
        SubscribeManager subscribeManager = new SubscribeManager();
        SignalManager signalManager = SignalManagerFactory.createSignalManager(signalBuildModels);

        Collection<Workflow> workflows = new ArrayList<>();
        for (WorkflowBuildModel workflowBuildModel : workflowBuildModels) {
            Workflow newWorkflow = createWorkflow(workflowBuildModel, signalManager, blockResponse,
                    remoteThreadManager, subscribeManager, workflowActionRepo, workflowRepo);
            subscribeManager.subscribe(newWorkflow);
            workflows.add(newWorkflow);
        }

        Collection<ActionBlock> actionBlocks = new ArrayList<>();
        for (BlockBuilderModel blockBuilderModel : blockBuilderModels) {
            ActionBlock actionBlock = ActionBlockFactory.createActionBlock(
                    blockBuilderModel,
                    workflowActionRepo,
                    subscribeManager,
                    signalManager,
                    workflowRepo,
                    sourceRequest,
                    blockRequest
            );
            subscribeManager.subscribe(actionBlock);
            actionBlocks.add(actionBlock);
        }

        return workflows;
    }


}
