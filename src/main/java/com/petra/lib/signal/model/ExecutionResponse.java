package com.petra.lib.signal.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.petra.lib.variable.process.ProcessVariable;
import com.petra.lib.workflow.model.ExecutionStatus;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.Collection;
import java.util.UUID;
import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Getter
public class ExecutionResponse {
    UUID scenarioId;
    Collection<ProcessVariable> consumerProcessVariables;
    Long producerBlockId;
    Long consumerBlockId;
    Long signalId;
    ExecutionStatus executionStatus;
    List<Long> consumerTransactionsId;

    public static ExecutionResponse fromJson(String json) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(json, ExecutionResponse.class);
    }
}
