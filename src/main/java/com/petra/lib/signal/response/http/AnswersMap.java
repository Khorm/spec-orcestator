package com.petra.lib.signal.response.http;

import com.petra.lib.signal.dto.ResponseDto;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

/**
 * Local storage for http
 */
class AnswersMap {

    private final Map<UUID, Answer> answers = new HashMap<>();

    static class Answer {
        @Setter
        private ResponseDto responseDto;
        private final Object mutex = new Object();
        private boolean blocked;
    }

    synchronized ResponseDto addKey(UUID key) {
        Answer prevAnswer = answers.put(key, new Answer());
        return Optional.ofNullable(prevAnswer)
                .map(answer -> answer.responseDto)
                .orElse(null);
    }

//    synchronized SignalTransferModel remove(UUID key) {
//        Answer answer = answers.remove(key);
//        return answer.signalTransferModel;
//    }

    void releaseAnswer(ResponseDto responseDto) {
        Answer answer;
        synchronized (this) {
            answer = answers.get(responseDto.getScenarioId());
            answer.responseDto = responseDto;
        }
        synchronized (answer.mutex) {
            answer.blocked = false;
            answer.mutex.notify();
        }
    }

    ResponseDto waitAnswer(UUID scenarioId) {
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
        synchronized (this) {
            return answers.remove(scenarioId).responseDto;
        }
    }

}
