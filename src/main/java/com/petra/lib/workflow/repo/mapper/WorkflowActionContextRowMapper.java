package com.petra.lib.workflow.repo.mapper;

import com.petra.lib.variable.value.ValuesContainer;
import com.petra.lib.variable.value.ValuesContainerFactory;
import com.petra.lib.workflow.context.WorkflowActionContext;
import com.petra.lib.workflow.enums.WorkflowActionState;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public class WorkflowActionContextRowMapper implements RowMapper<WorkflowActionContext> {
    @Override
    public WorkflowActionContext mapRow(ResultSet rs, int rowNum) throws SQLException {
        UUID scenarioId = UUID.fromString(rs.getString("scenario_id"));
        Long workflowId = rs.getLong("workflow_id");
        Long execBlock = rs.getLong("exec_block");
        WorkflowActionState workflowActionState
                = WorkflowActionState.valueOf(rs.getString("workflow_action_state"));
        ValuesContainer blockVariables = ValuesContainerFactory.fromJson(rs.getString("block_variables"));
        Long nextSignalId = rs.getLong("next_signal_id");
        ValuesContainer callingSignalVariables
                = ValuesContainerFactory.fromJson(rs.getString("calling_signal_variables"));
        Long callingSignalId = rs.getLong("calling_signal_id");
        int loadingGroup = rs.getInt("loading_group");
        Set<Long> loadedSources = Arrays.stream(((Long[]) rs.getArray("loaded_sources").getArray()))
                .collect(Collectors.toSet());
        WorkflowActionContext workflowActionContext = new WorkflowActionContext(scenarioId, workflowId,
                execBlock, callingSignalVariables, callingSignalId, loadedSources);
        workflowActionContext.setWorkflowState(workflowActionState);
        workflowActionContext.setBlockVariables(blockVariables);
        workflowActionContext.setNextSignalId(nextSignalId);
        workflowActionContext.setLoadingGroup(loadingGroup);
        return workflowActionContext;
    }
}
