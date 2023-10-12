package com.petra.lib.workflow.new_workflow.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.petra.lib.block.ProcessValue;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import java.util.Collection;
import java.util.UUID;

@RequiredArgsConstructor
public class WorkflowRepoImpl implements WorkflowRepo {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public int getExecutedNodesCount(UUID scenarioId, Long workflowId) {
        String SQL = "SELECT COUNT(*) FROM (SELECT * FROM WORKFLOW_HISTORY WHERE scenario_id = :scenarioID AND workflow_id = :workflowId)";
        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("scenarioID", scenarioId)
                .addValue("workflowId", workflowId);
        return jdbcTemplate.queryForObject(SQL, namedParameters, Integer.class);

    }

    @Override
    public void setNodeExecuted(UUID scenarioId, Long workflowId, Long nodeId,  Collection<ProcessValue> resultVariables) throws JsonProcessingException {
        String SQL = "INSERT INTO WORKFLOW_HISTORY VALUES(scenarioId, workflowId, nodeId, resultValues)";
        ObjectMapper objectMapper = new ObjectMapper();
        String values = objectMapper.writeValueAsString(resultVariables);
        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("scenarioID", scenarioId)
                .addValue("workflowId", workflowId)
                .addValue("nodeId", nodeId)
                .addValue("resultValues", values);
        jdbcTemplate.update(SQL, namedParameters);
    }

    @Override
    public boolean isNodeExecuted(UUID scenarioId,Long workflowId, Long nodeId) {
        String SQL = "SELECT EXISTS (SELECT * FROM WORKFLOW_HISTORY WHERE scenario_id = :scenarioId AND nodeId = :nodeId AND workflowId = :workflowId)";
        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("scenarioID", scenarioId)
                .addValue("workflowId", workflowId)
                .addValue("nodeId", nodeId);
        return jdbcTemplate.queryForObject(SQL, namedParameters, Boolean.class);
    }

    @Override
    public Collection<ProcessValue> getNodeExecutionResults(UUID scenarioId, Long workflowId, Long nodeId) throws JsonProcessingException {
        String SQL = "SELECT result_values FROM WORKFLOW_HISTORY WHERE scenario_id = :scenarioId AND nodeId = :nodeId AND workflowId = :workflowId";
        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("scenarioID", scenarioId)
                .addValue("workflowId", workflowId)
                .addValue("nodeId", nodeId);
        String values = jdbcTemplate.queryForObject(SQL, namedParameters, String.class);
        ObjectMapper om = new ObjectMapper();
        return om.readValue(values, new TypeReference<Collection<ProcessValue>>() {});
    }

}
