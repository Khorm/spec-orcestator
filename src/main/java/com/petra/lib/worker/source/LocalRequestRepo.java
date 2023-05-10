package com.petra.lib.worker.source;

import com.petra.lib.manager.block.JobContext;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

class LocalRequestRepo implements RequestRepo {
    //TODO ОБЯЗАТЕЛЬНО ПЕРЕПИСАТЬ НА scenarioId + blockId
    private final Map<UUID, ContextModel> uuidRequestDataMap = new HashMap<>();

    public synchronized void addNewRequestData(JobContext context) {
        if (uuidRequestDataMap.containsKey(context.getScenarioId())) return;
        ContextModel contextData = new ContextModel(context);
        uuidRequestDataMap.put(context.getScenarioId(), contextData);
    }

    public synchronized void addExecutedSourceId(UUID scenarioId, Long signal) {
        uuidRequestDataMap.get(scenarioId).setExecutedSourceSignal(signal);
    }

    public synchronized Set<Long> getExecutedSignals(UUID scenarioId) {
        return uuidRequestDataMap.get(scenarioId).getExecutedSourceSignalIds();
    }

    public synchronized JobContext getExecutionContext(UUID scenarioId) {
        return uuidRequestDataMap.get(scenarioId).getContext();
    }

    public synchronized ContextModel clear(UUID scenarioId) {
        return uuidRequestDataMap.remove(scenarioId);
    }

    public synchronized boolean checkIsScenarioContains(UUID scenarioId) {
        return uuidRequestDataMap.containsKey(scenarioId);
    }

    public synchronized int getReceivedSignalsSize(UUID scenarioId) {
        return uuidRequestDataMap.get(scenarioId).executedSourceSignalsSize();
    }
}
