package com.petra.lib.worker.repo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.petra.lib.manager.block.BlockId;
import com.petra.lib.manager.block.ProcessVariableDto;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.orm.jpa.JpaTransactionManager;

import java.util.Collection;
import java.util.UUID;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class JobRepository {
    NamedParameterJdbcTemplate jdbcTemplate;

    public JobRepository(JpaTransactionManager jpaTransactionManager) {
        jdbcTemplate = new NamedParameterJdbcTemplate(jpaTransactionManager.getDataSource());
    }

    public boolean isExecutedBefore(UUID scenarioId, BlockId blockId) {
        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("scenarioId", scenarioId)
                .addValue("blockId", blockId.getBlockId())
                .addValue("blockVersion", blockId.getVersion().toString());

        Integer count = jdbcTemplate.queryForObject("SELECT count(*) FROM JOB_HISTORY WHERE SCENARIO_ID" +
                " = :scenarioId AND BLOCK_ID = :blockId AND BLOCK_VERSION = :blockVersion", namedParameters, Integer.class);
        return count.equals(1);
    }

    public int saveExecution(UUID scenarioId, BlockId blockId, String variablesJson) {
        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("scenarioId", scenarioId)
                .addValue("blockId", blockId.getBlockId())
                .addValue("blockVersion", blockId.getVersion().toString())
                .addValue("result_variables", variablesJson);

        return jdbcTemplate.update("INSERT INTO JOB_HISTORY VALUES (:scenarioId, :blockId,:blockVersion, :result_variables)", namedParameters);

    }

    public Collection<ProcessVariableDto> getVariables(UUID scenarioId, BlockId blockId) throws JsonProcessingException {
        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("scenarioId", scenarioId)
                .addValue("blockId", blockId.getBlockId())
                .addValue("blockVersion", blockId.getVersion().toString());

        String SQL = "SELECT result_variables FROM JOB_HISTORY WHERE scenario_id = :scenarioId AND block_id = :blockId AND BLOCK_VERSION = :blockVersion";
        String values = jdbcTemplate.queryForObject(SQL, namedParameters, String.class);
        return new ObjectMapper().readValue(values, new TypeReference<>() {
        });
    }
}
