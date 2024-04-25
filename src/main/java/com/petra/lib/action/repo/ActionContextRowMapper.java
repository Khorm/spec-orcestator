package com.petra.lib.action.repo;

import com.petra.lib.action.ActionContext;
import com.petra.lib.action.BlockState;
import com.petra.lib.variable.value.ValuesContainer;
import com.petra.lib.variable.value.ValuesContainerFactory;
import com.petra.lib.workflow.context.WorkflowActionContext;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class ActionContextRowMapper  implements RowMapper<ActionContext> {
    @Override
    public ActionContext mapRow(ResultSet rs, int rowNum) throws SQLException {
        Long actionId = rs.getLong("action_id");
        UUID scenarioId = UUID.fromString(rs.getString("scenario_id"));
        BlockState actionState = BlockState.valueOf(rs.getString("action_state"));
        ValuesContainer actionValues = ValuesContainerFactory.fromJson(rs.getString("action_variables"));
        Long requestWorkflowId = rs.getLong("request_workflow_id");
        String requestServiceName = rs.getString("request_service_name");
        ActionContext actionContext = new ActionContext(actionId, scenarioId, actionValues,
                requestWorkflowId, requestServiceName);
        actionContext.setActionState(actionState);
        return actionContext;

    }
}
