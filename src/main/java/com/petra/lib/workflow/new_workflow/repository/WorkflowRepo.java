package com.petra.lib.workflow.new_workflow.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.petra.lib.state.variable.neww.ProcessValue;

import java.util.Collection;
import java.util.UUID;

public interface WorkflowRepo {


    int getExecutedNodesCount(UUID scenarioId, Long workflowId);
    void setNodeExecuted(UUID scenarioId, Long workflowId, Long nodeId,  Collection<ProcessValue> resultVariables) throws JsonProcessingException;
    boolean isNodeExecuted(UUID scenarioId,Long workflowId, Long nodeId);
    Collection<ProcessValue> getNodeExecutionResults(UUID scenarioId, Long workflowId, Long nodeId) throws JsonProcessingException;
}
