package com.petra.lib.action.new_action;

import com.petra.lib.action.model.RollbackModel;
import com.petra.lib.manager.ExecutionContext;
import com.petra.lib.workflow.model.ExecutionResponseRowMapper;
import com.petra.lib.workflow.model.RollbackRowMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class ActionRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final ExecutionResponseRowMapper executionResponseRowMapper;
    private final RollbackRowMapper rollbackRowMapper;

    public Boolean isActionExecutedBefore(UUID scenarioId, Long actionId){
        String SQL = "SELECT EXISTS (SELECT * FROM ACTION_HISTORY WHERE scenario_id = :scenarioID AND action_id = :actionId)";
        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("scenarioID", scenarioId)
                .addValue("actionId", actionId);
        return jdbcTemplate.queryForObject(SQL, namedParameters, Boolean.class);
    }

    public ExecutionContext getExecutionResult(UUID scenarioId, Long actionId){
        String SQL = "SELECT * FROM ACTION_HISTORY WHERE scenario_id = :scenarioID AND action_id = :actionId";
        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("scenarioID", scenarioId)
                .addValue("actionId", actionId);
        return jdbcTemplate.queryForObject(SQL, namedParameters, executionResponseRowMapper);
    }

    public void saveExecutionResult(ExecutionContext executionResponse, ExecutionResult executionResult){
        SimpleJdbcInsert simpleJdbcInsert =
                new SimpleJdbcInsert(jdbcTemplate.getJdbcTemplate()).withTableName("ACTION_HISTORY");

        Map<String, Object> parameters = executionResponseRowMapper.createInsetParameters(executionResponse);
        simpleJdbcInsert.execute(parameters);
    }

    public void saveRollback(RollbackModel rollbackRequest){
        SimpleJdbcInsert simpleJdbcInsert =
                new SimpleJdbcInsert(jdbcTemplate.getJdbcTemplate()).withTableName("ACTION_ROLLBACK");
        Map<String, Object> parameters = rollbackRowMapper.createInsetParameters(rollbackRequest);
        simpleJdbcInsert.execute(parameters);
    }

    public RollbackModel getRollbackModel(UUID scenarioId, Long actionId){
        String SQL = "SELECT * FROM ACTION_ROLLBACK WHERE scenario_id = :scenarioID AND action_id = :actionId";
        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("scenarioID", scenarioId)
                .addValue("actionId", actionId);
        return jdbcTemplate.queryForObject(SQL, namedParameters, rollbackRowMapper);
    }

//    public void saveActionExecution(ActionExecution actionExecution){
//
//    }
//
//    public void saveActionCompensationContext(ActionCompensationContext actionCompensationContext){
//
//    }
//
//    public ActionCompensationContext getCompensation(Long actionId, UUID scenarioId){
//
//    }
//
//    public void saveActionCompensation(ActionCompensation actionCompensation){
//
//    }
}
