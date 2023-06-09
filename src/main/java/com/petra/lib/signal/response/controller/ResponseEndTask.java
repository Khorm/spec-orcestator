package com.petra.lib.signal.response.controller;

import com.petra.lib.manager.block.BlockId;
import com.petra.lib.manager.block.JobContext;
import com.petra.lib.manager.block.ProcessVariableDto;
import com.petra.lib.signal.response.ResponseSignal;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.Collection;
import java.util.UUID;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class ResponseEndTask implements Runnable{

    Collection<ProcessVariableDto> signalAnswerVariables;
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
