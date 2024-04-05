package com.petra.lib.workflow.repo;

import com.petra.lib.workflow.context.WorkflowActionContext;

public interface WorkflowActionRepo {
//    Optional<WorkflowActionContext> findContext(UUID scenarioId, Long workflowId, Long actionId);

//    void updateWorkflowContextState(UUID scenarioId, BlockId workflowId, BlockId actionId, WorkflowActionState workflowActionState);
//    void updateWorkflowContextStateAndOuterSignals(UUID scenarioId,
//                                                   BlockId workflowId,
//                                                   BlockId actionId,
//                                                   WorkflowActionState workflowActionState,
//                                                   Collection<ResponseSignal> responseSignals);

    /**
     * ��������� �������������� ��������
     * @param workflowActionContext
     * @return
     * true - �������� ��������
     * false - �������� ��� ����������
     *
     */
    boolean saveContext(WorkflowActionContext workflowActionContext);

    /**
     * ��������� ��������
     * @param workflowActionContext
     * @return
     * true - ������� �������� ����� ���������
     * false - �� ������� �������� ����� ���������
     */
    boolean updateContext(WorkflowActionContext workflowActionContext);

//    Collection<WorkflowActionContext> findExecutedWorkflows(UUID scenarioId, BlockId workflowId);
}
