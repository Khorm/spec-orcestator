package com.petra.lib.XXXXXXsignal.response.controller;

import com.petra.lib.block.BlockId;
import com.petra.lib.block.ProcessValue;
import com.petra.lib.XXXXXXsignal.SignalId;

import java.util.Collection;
import java.util.UUID;

public interface ResponseReadyListener {
    void answerToRequest(Collection<ProcessValue> signalAnswerVariables,
                         SignalId signalId,
                         BlockId requestBlockId,
                         UUID scenarioId,
                         BlockId responseBlockId);

    void errorToRequest(SignalId signalId,
                        BlockId requestBlockId,
                        UUID scenarioId,
                        BlockId responseBlockId);

    void idempotencyErrorToRequest(Collection<ProcessValue> signalAnswerVariables,
                                  SignalId signalId,
                                  BlockId requestBlockId,
                                  UUID scenarioId,
                                  BlockId responseBlockId);
}
