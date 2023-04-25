package com.petra.lib.signal.source;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.petra.lib.manager.block.JobContext;

import java.util.Set;
import java.util.UUID;

public interface RequestRepo {
    void addNewRequestData(JobContext context) throws JsonProcessingException;
    Set<Long> getExecutedSignals(UUID scenarioId);
    ContextModel clear(UUID scenarioId);
    boolean checkIsScenarioContains(UUID scenarioId);
    JobContext getExecutionContext(UUID scenarioId);
    void addExecutedSourceId(UUID scenarioId, Long signal);
    int getReceivedSignalsSize(UUID scenarioId);
}
