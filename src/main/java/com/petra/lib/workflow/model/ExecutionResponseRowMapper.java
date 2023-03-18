package com.petra.lib.workflow.model;

import com.petra.lib.signal.model.ExecutionResponse;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.UUID;

@Component
public class ExecutionResponseRowMapper /*implements RowMapper<ExecutionResponse>*/ {

//    @Override
//    public ExecutionResponse mapRow(ResultSet rs, int rowNum) throws SQLException {
//        UUID scenarioId = rs.getObject("scenario_id", UUID.class);
//        Long majorVersion = rs.getLong("version_major");
//        Long minorVersion = rs.getLong("version_minor");
////        String
////
////        ExecutionResponse executionResponse = new ExecutionResponse()
//        return null;
//    }
//
//
//    public Map<String, Object> createInsetParameters(ExecutionResponse executionResponse){
//
//    }
}
