package com.petra.lib.signal.source;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
class RequestController {

////    Map<UUID, ContextData> uuidRequestDataMap = new HashMap<>();
////    Map<UUID, Thread> timerMap = new HashMap<>();
////    Long connectionTimeout;
//    private final NamedParameterJdbcTemplate jdbcTemplate;
//
//    void addNewRequestData(ExecutionContext context/*, ExecutionHandler executionHandler*/) throws JsonProcessingException {
////        if (uuidRequestDataMap.containsKey(context.getScenarioId())) return;
////
////        Thread timer = new Thread(() -> {
////            try {
////                TimeUnit.MILLISECONDS.sleep(connectionTimeout);
////                timeout(context.getScenarioId());
////            } catch (InterruptedException e) {
////                //stop timer
////            }
////        });
////        timer.start();
////        timerMap.put(context.getScenarioId(), timer);
////
////        ContextData contextData = new ContextData(context, executionHandler);
////        uuidRequestDataMap.put(context.getScenarioId(), contextData);
//        SqlParameterSource namedParameters = new MapSqlParameterSource()
//                .addValue("scenarioId", context.getScenarioId())
//                .addValue("context", context.getVariablesJson());
//        jdbcTemplate.update("INSERT INTO SOURCE_REQUEST_HISTORY VALUES(scenarioId, context)", namedParameters);
//
//    }
//
//    void addExecutedSourceId(UUID scenarioId, Long signal){
//        SqlParameterSource namedParameters = new MapSqlParameterSource()
//                .addValue("scenarioId", scenarioId);
//        String sourcesList = jdbcTemplate.queryForObject("SELECT EXECUTED_SOURCES FROM SOURCE_REQUEST_HISTORY WHERE SCENARIO_ID = :scenarioId",
//                namedParameters, String.class);
//
//        new ObjectMapper()
//
//        jdbcTemplate.update("UPDATE SOURCE_REQUEST_HISTORY SET ", context.getVariablesJson());
////        uuidRequestDataMap.get(scenarioId).setExecutedSourceSignal(signal);
//    }
//
////    synchronized ExecutionHandler getExecutionHandler(UUID scenarioId){
////        return uuidRequestDataMap.get(scenarioId).getExecutionHandler();
////    }
//
//    Set<Long> getExecutedSignals(UUID scenarioId){
////        return uuidRequestDataMap.get(scenarioId).getExecutedSourceSignalIds();
//    }
//
//    ExecutionContext getExecutionContext(UUID scenarioId){
////        return uuidRequestDataMap.get(scenarioId).getContext();
//    }
//
//    ContextModel clear(UUID scenarioId){
////        Thread timer = timerMap.get(scenarioId);
////        timer.interrupt();
////        return uuidRequestDataMap.remove(scenarioId);
//    }
//
//    boolean checkIsScenarioContains(UUID scenarioId){
////        return uuidRequestDataMap.containsKey(scenarioId);
//    }
//
//    int getReceivedSignalsSize(UUID scenarioId){
////        return uuidRequestDataMap.get(scenarioId).executedSourceSignalsSize();
//    }
//
////    private synchronized void timeout(UUID scenarioId){
////        if (!uuidRequestDataMap.containsKey(scenarioId)) return;
////        ContextData contextData = clear(scenarioId);
////        contextData.getExecutionHandler().executeNext(contextData.getContext(), ExecutionState.ERROR);
////    }


}
