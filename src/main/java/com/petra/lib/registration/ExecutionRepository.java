package com.petra.lib.registration;

import com.petra.lib.workflow.model.ExecutionResponseRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.UUID;

public class ExecutionRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final ExecutionResponseRowMapper executionResponseRowMapper;

    public boolean isExecutedBefore(UUID scenarioId, Long blockId){

    }


    public void saveExecution(UUID scenarioId, Long blockId, Integer groupId, String variablesJson){

    }
}
