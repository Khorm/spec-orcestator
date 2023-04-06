package com.petra.lib.signal.source;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.petra.lib.manager.ExecutionContext;
import org.bouncycastle.cert.ocsp.Req;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

class LocalRequestRepo implements RequestRepo {
    Map<UUID, ContextModel> uuidRequestDataMap = new HashMap<>();


    public void addNewRequestData(ExecutionContext context) throws JsonProcessingException {
        if (uuidRequestDataMap.containsKey(context.getScenarioId())) return;
//
//        Thread timer = new Thread(() -> {
//            try {
//                TimeUnit.MILLISECONDS.sleep(connectionTimeout);
//                timeout(context.getScenarioId());
//            } catch (InterruptedException e) {
//                //stop timer
//            }
//        });
//        timer.start();
//        timerMap.put(context.getScenarioId(), timer);

        ContextModel contextData = new ContextModel(context);
        uuidRequestDataMap.put(context.getScenarioId(), contextData);


    }

    public void addExecutedSourceId(UUID scenarioId, Long signal) {
        uuidRequestDataMap.get(scenarioId).setExecutedSourceSignal(signal);
    }

//    synchronized ExecutionHandler getExecutionHandler(UUID scenarioId){
//        return uuidRequestDataMap.get(scenarioId).getExecutionHandler();
//    }

    public Set<Long> getExecutedSignals(UUID scenarioId) {
        return uuidRequestDataMap.get(scenarioId).getExecutedSourceSignalIds();
    }

    public ExecutionContext getExecutionContext(UUID scenarioId) {
        return uuidRequestDataMap.get(scenarioId).getContext();
    }

    public ContextModel clear(UUID scenarioId) {
//        Thread timer = timerMap.get(scenarioId);
//        timer.interrupt();
        return uuidRequestDataMap.remove(scenarioId);
    }

    public boolean checkIsScenarioContains(UUID scenarioId) {
        return uuidRequestDataMap.containsKey(scenarioId);
    }

    public int getReceivedSignalsSize(UUID scenarioId) {
        return uuidRequestDataMap.get(scenarioId).executedSourceSignalsSize();
    }

//    private synchronized void timeout(UUID scenarioId){
//        if (!uuidRequestDataMap.containsKey(scenarioId)) return;
//        ContextData contextData = clear(scenarioId);
//        contextData.getExecutionHandler().executeNext(contextData.getContext(), ExecutionState.ERROR);
//    }
}
