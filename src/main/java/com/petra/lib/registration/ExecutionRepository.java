package com.petra.lib.registration;

import com.petra.lib.workflow.model.ExecutionResponseRowMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import javax.sql.DataSource;
import java.util.UUID;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ExecutionRepository {
    NamedParameterJdbcTemplate jdbcTemplate;

    public ExecutionRepository(DataSource dataSource){
        jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    public boolean isExecutedBefore(UUID scenarioId, Long blockId){
        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("scenarioId", scenarioId)
                .addValue("blockId", blockId);

        return jdbcTemplate.queryForObject("EXISTS( SELECT * FROM EXECUTION_HISTORY WHERE SCENARIO_ID = :scenarioId" +
                " AND BLOCK_ID = :blockId)", namedParameters, Boolean.class);
    }

    public void saveExecution(UUID scenarioId, Long blockId, String variablesJson){
        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("scenarioId", scenarioId)
                .addValue("blockId", blockId)
                .addValue("variables", variablesJson);
        jdbcTemplate.update("INSERT INTO EXECUTION_HISTORY VALUES (scenarioId, blockId, variables)", namedParameters);
    }
}
