package com.petra.lib.workflow.observer;

import com.petra.lib.signal.model.ExecutionResponse;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Менеджер подписки на элементы сценария
 */
public class ScenarioObserverManager {
//
//    private final Map<ScenarioSignalKeeper, ScenarioListener> scenarioSignalKeeperMap = new ConcurrentHashMap<>();
//
//
//    public void subscribe(UUID scenarioId, Long blockId, ScenarioListener scenarioListener){
//        scenarioSignalKeeperMap.put(new ScenarioSignalKeeper(scenarioId, blockId), scenarioListener);
//    }
//
//    public void execute(ExecutionResponse executionResponseDto){
//        ScenarioListener scenarioHandler = scenarioSignalKeeperMap.remove(
//                new ScenarioSignalKeeper(executionResponseDto.getScenarioId(), executionResponseDto.getBlockId()));
//        scenarioHandler.update(executionResponseDto);
//    }
}
