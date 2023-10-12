package com.petra.lib.state.variable.group.repo;

import com.petra.lib.context.ActionContext;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

 class LocalContextRepo implements ContextRepo {
    private final Map<Long, Map<UUID, ContextModel>> requestDataMap = new HashMap<>();

    @Override
    public synchronized void addNewRequestData(ActionContext context) {
        if (!requestDataMap.containsKey(context.getBlockId())) {
            requestDataMap.put(context.getBlockId(), new HashMap<>());
        }
        if (requestDataMap.get(context.getBlockId()).containsKey(context.getScenarioId())) {
            return;
        }

        ContextModel contextData = new ContextModel(context);
        requestDataMap.get(context.getBlockId()).put(context.getScenarioId(), contextData);
    }

    @Override
    public synchronized ActionContext getExecutionContext(UUID scenarioId, Long blockId) {
        return requestDataMap.get(blockId).get(scenarioId).getContext();
    }

    @Override
    public synchronized ContextModel clear(UUID scenarioId, Long blockId) {
        return requestDataMap.get(blockId).remove(scenarioId);
    }

    @Override
    public synchronized Set<Long> setExecution(ActionContext context, Long groupId) {
        ContextModel contextModel = requestDataMap.get(context.getBlockId()).get(context.getScenarioId());
        contextModel.setFilledGroupId(groupId);
        return contextModel.getExecutedSourceSignalIds();
    }

//    public synchronized boolean checkIsScenarioContains(UUID scenarioId, Long blockId) {
//        return requestDataMap.get(blockId).containsKey(scenarioId);
//    }
//
//    public synchronized int getReceivedSignalsSize(UUID scenarioId, Long blockId) {
//        return requestDataMap.get(blockId).get(scenarioId).executedSourceSignalsSize();
//    }

}
