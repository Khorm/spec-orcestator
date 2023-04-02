package com.petra.lib.signal.sync;

import com.petra.lib.signal.model.SignalTransferModel;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

class AnswersMap {

    private final Map<UUID, SignalTransferModel> answers = new HashMap<>();

    synchronized void putIfExists(SignalTransferModel signalTransferModel){
        if (answers.containsKey(signalTransferModel.getScenarioId())){
            answers.put(signalTransferModel.getScenarioId(), signalTransferModel);
        }else {
            throw new IllegalStateException("No answers found " + signalTransferModel.getScenarioId());
        }
    }

    synchronized SignalTransferModel addKey(UUID key){
        return answers.put(key, null);
    }

    synchronized SignalTransferModel remove(UUID key){
        return answers.remove(key);
    }
}
