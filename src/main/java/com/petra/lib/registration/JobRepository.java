package com.petra.lib.registration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.petra.lib.variable.process.ProcessVariable;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import javax.sql.DataSource;
import java.util.Collection;
import java.util.UUID;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class JobRepository {
    NamedParameterJdbcTemplate jdbcTemplate;

    public JobRepository(DataSource dataSource) {
        jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    public boolean isExecutedBefore(UUID scenarioId, Long blockId) {
        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("scenarioId", scenarioId)
                .addValue("blockId", blockId);

        return jdbcTemplate.queryForObject("EXISTS( SELECT * FROM JOB_HISTORY WHERE SCENARIO_ID = :scenarioId" +
                " AND BLOCK_ID = :blockId)", namedParameters, Boolean.class);
    }

    public void saveExecution(UUID scenarioId, Long blockId, String variablesJson) {
        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("scenarioId", scenarioId)
                .addValue("blockId", blockId)
                .addValue("result_variables", variablesJson);
        jdbcTemplate.update("INSERT INTO JOB_HISTORY VALUES (scenarioId, blockId, result_variables)", namedParameters);
    }

    public Collection<ProcessVariable> getVariables(UUID scenarioId, Long blockId) throws JsonProcessingException {
        String SQL = "SELECT variables FROM JOB_HISTORY WHERE scenario_id = :scenarioId AND block_id = :blockId";
        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("scenarioId", scenarioId)
                .addValue("blockId", blockId);
        String values = jdbcTemplate.queryForObject(SQL, namedParameters, String.class);
        return new ObjectMapper().readValue(values, new TypeReference<Collection<ProcessVariable>>() {});
    }
}
