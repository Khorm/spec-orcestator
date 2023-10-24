package com.petra.lib.XXXXXXsignal.response.controller;

import com.petra.lib.block.BlockId;
import com.petra.lib.environment.context.ProcessValue;
import com.petra.lib.XXXXXXsignal.response.ResponseSignal;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.Collection;
import java.util.UUID;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class ResponseEndTask implements Runnable{

    Collection<ProcessValue> signalAnswerVariables;
    BlockId requestBlockId;
    UUID scenarioId;
    BlockId responseBlockId;
    ResponseSignal responseSignal;
    boolean execWithoutErrors;

    @Override
    public void run() {
        if (execWithoutErrors){
            responseSignal.setAnswer(signalAnswerVariables, requestBlockId, scenarioId, responseBlockId);
        }else {
            responseSignal.setError(requestBlockId, scenarioId, responseBlockId);
        }
    }
}
