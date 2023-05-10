package com.petra.lib.signal.sync;

import com.petra.lib.signal.model.SignalTransferModel;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

class AnswersMap {

    private final Map<UUID, Answer> answers = new HashMap<>();

    static class Answer {
        @Setter
        private SignalTransferModel signalTransferModel;
        private final Object mutex = new Object();
        private boolean blocked;
    }

    synchronized void putOnlyIfExists(SignalTransferModel signalTransferModel) {
        if (answers.containsKey(signalTransferModel.getScenarioId())) {
            answers.get(signalTransferModel.getScenarioId()).signalTransferModel = signalTransferModel;
        } else {
            throw new IllegalStateException("No answers found " + signalTransferModel.getScenarioId());
        }
    }

    synchronized SignalTransferModel addKey(UUID key) {
        Answer prevAnswer = answers.put(key, new Answer());
        return Optional.ofNullable(prevAnswer)
                .map(answer -> answer.signalTransferModel)
                .orElse(null);
    }

    synchronized SignalTransferModel remove(UUID key) {
        Answer answer = answers.remove(key);
//        synchronized (answer.mutex) {
//            answer.blocked = false;
//            answer.mutex.notify();
//        }
        return answer.signalTransferModel;
    }

    void releaseAnswer(UUID key) {
        Answer answer;
        synchronized (this) {
            answer = answers.get(key);
        }
        synchronized (answer.mutex) {
            answer.blocked = false;
            answer.mutex.notify();
        }
    }

    void waitAnswer(UUID scenarioId) {
        Answer answer;
        synchronized (this) {
            answer = answers.get(scenarioId);
        }
        synchronized (answer.mutex) {
            {
                try {
                    answer.blocked = true;
                    answer.mutex.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            while (answers.get(scenarioId).blocked) ;
        }
    }

}
