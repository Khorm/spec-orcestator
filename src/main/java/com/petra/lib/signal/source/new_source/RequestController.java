package com.petra.lib.signal.source.new_source;

import com.petra.lib.manager.ExecutionContext;
import com.petra.lib.manager.ExecutionHandler;
import com.petra.lib.manager.state.ExecutionState;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
class RequestController {

    Map<UUID, ContextData> uuidRequestDataMap = new HashMap<>();
    Map<UUID, Thread> timerMap = new HashMap<>();
    Long connectionTimeout;

    synchronized void addNewRequestData(ExecutionContext context, ExecutionHandler executionHandler){
        if (uuidRequestDataMap.containsKey(context.getScenarioId())) return;

        Thread timer = new Thread(() -> {
            try {
                TimeUnit.MILLISECONDS.sleep(connectionTimeout);
                timeout(context.getScenarioId());
            } catch (InterruptedException e) {
                //stop timer
            }
        });
        timer.start();
        timerMap.put(context.getScenarioId(), timer);

        ContextData contextData = new ContextData(context, executionHandler);
        uuidRequestDataMap.put(context.getScenarioId(), contextData);
    }

    synchronized void addExecutedSourceId(UUID scenarioId, Long signal){
        uuidRequestDataMap.get(scenarioId).setExecutedSourceSignal(signal);
    }

    synchronized ExecutionHandler getExecutionHandler(UUID scenarioId){
        return uuidRequestDataMap.get(scenarioId).getExecutionHandler();
    }

    synchronized Set<Long> getExecutedSignals(UUID scenarioId){
        return uuidRequestDataMap.get(scenarioId).getExecutedSourceSignalIds();
    }

    synchronized ExecutionContext getExecutionContext(UUID scenarioId){
        return uuidRequestDataMap.get(scenarioId).getContext();
    }

    synchronized ContextData clear(UUID scenarioId){
        Thread timer = timerMap.get(scenarioId);
        timer.interrupt();
        return uuidRequestDataMap.remove(scenarioId);
    }

    synchronized boolean checkIsScenarioContains(UUID scenarioId){
        return uuidRequestDataMap.containsKey(scenarioId);
    }

    synchronized int getReceivedSignalsSize(UUID scenarioId){
        return uuidRequestDataMap.get(scenarioId).executedSourceSignalsSize();
    }

    private synchronized void timeout(UUID scenarioId){
        if (!uuidRequestDataMap.containsKey(scenarioId)) return;
        ContextData contextData = clear(scenarioId);
        contextData.getExecutionHandler().executeNext(contextData.getContext(), ExecutionState.REQUEST_SOURCE_DATA_ERROR);
    }


}
