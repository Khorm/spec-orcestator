package com.petra.lib.signal.source;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.petra.lib.manager.ExecutionContext;

import java.util.Set;
import java.util.UUID;

public interface RequestRepo {
    void addNewRequestData(ExecutionContext context) throws JsonProcessingException;
    Set<Long> getExecutedSignals(UUID scenarioId);
    ContextModel clear(UUID scenarioId);
    boolean checkIsScenarioContains(UUID scenarioId);
    ExecutionContext getExecutionContext(UUID scenarioId);
    void addExecutedSourceId(UUID scenarioId, Long signal);
    int getReceivedSignalsSize(UUID scenarioId);
}
