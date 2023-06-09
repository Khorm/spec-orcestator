package com.petra.lib.signal.request.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

class AnswerListenerMap{

    private HashMap<Long, Map<UUID, AnswerListener>> requestListeners = new HashMap<>();

    synchronized void addAnswerListener(UUID scenarioId, Long requestSignalId, AnswerListener answerListener){
        if (!requestListeners.containsKey(requestSignalId)){
            requestListeners.put(requestSignalId, new HashMap<>());
        }
        requestListeners.get(requestSignalId).put(scenarioId, answerListener);

    }

    synchronized AnswerListener getAnswerListener(UUID scenarioId, Long requestSignalId){
        return requestListeners.get(requestSignalId).remove(scenarioId);
    }
}
