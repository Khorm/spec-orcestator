package com.petra.lib.workflow.repository;

import com.petra.lib.signal.model.ExecutionResponse;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public class WorkflowRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate = null;

    Boolean isWorkflowRequestedBefore(UUID scenarioId){
        String SQL = "SELECT EXISTS (SELECT * FROM workflow_history WHERE scenario_id = :scenarioID)";
        SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("scenarioID", scenarioId);
        return jdbcTemplate.queryForObject(SQL, namedParameters, Boolean.class);
    }

    Optional<ExecutionResponse> getExecutionResponse(UUID scenarioId){
        return null;
    }
}
