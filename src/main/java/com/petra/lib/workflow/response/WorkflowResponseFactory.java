package com.petra.lib.workflow.response;

import com.petra.lib.remote.response.block.BlockResponse;
import com.petra.lib.remote.thread.RemoteThreadManager;
import com.petra.lib.workflow.repo.WorkflowRepo;

public final class WorkflowResponseFactory {

    public static WorkflowResponseExecutor createWorkflowResponseExecutor(Long callingSignal,
                                                                          BlockResponse blockResponse,
                                                                          RemoteThreadManager remoteThreadManager,
                                                                          WorkflowRepo workflowRepo){
        return new WorkflowResponseExecutor(callingSignal, blockResponse, remoteThreadManager, workflowRepo);
    }
}
