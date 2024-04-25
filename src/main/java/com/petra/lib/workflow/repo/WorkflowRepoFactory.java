package com.petra.lib.workflow.repo;

import com.petra.lib.transaction.TransactionManager;

public final class WorkflowRepoFactory {

    public static WorkflowRepo createWorkflowRepo(TransactionManager transactionManager){
        return new WorkflowRepoImpl(transactionManager);
    }

    public static WorkflowActionRepo createWorkflowActionRepo(TransactionManager transactionManager){
        return new WorkflowActionRepoImpl(transactionManager);
    }
}
