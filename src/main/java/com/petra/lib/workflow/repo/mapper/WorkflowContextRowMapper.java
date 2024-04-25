package com.petra.lib.workflow.repo.mapper;

import com.petra.lib.action.BlockState;
import com.petra.lib.variable.value.ValuesContainer;
import com.petra.lib.variable.value.ValuesContainerFactory;
import com.petra.lib.workflow.context.WorkflowActionContext;
import com.petra.lib.workflow.context.WorkflowContext;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class WorkflowContextRowMapper implements RowMapper<WorkflowContext> {
    @Override
    public WorkflowContext mapRow(ResultSet rs, int rowNum) throws SQLException {
        UUID scenarioId = UUID.fromString(rs.getString("scenario_id"));
        Long producerId = rs.getLong("producer_id");
        Long consumerId = rs.getLong("consumer_id");
        Long signalId = rs.getLong("signal_id");
        ValuesContainer signalVariables = ValuesContainerFactory.fromJson(rs.getString("signal_variables"));
        BlockState state = BlockState.valueOf(rs.getString("workflow_state"));
        ValuesContainer responseVariables = ValuesContainerFactory.fromJson(rs.getString("response_variables"));
        String requestServiceName = rs.getString("request_service_name ");
        WorkflowContext workflowContext = new WorkflowContext(scenarioId, producerId, consumerId, signalId, signalVariables,
                requestServiceName);
        workflowContext.setWorkflowState(state);
        workflowContext.setResponseVariables(responseVariables);
        return workflowContext;
    }
}
