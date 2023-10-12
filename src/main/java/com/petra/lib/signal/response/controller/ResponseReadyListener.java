package com.petra.lib.signal.response.controller;

import com.petra.lib.block.BlockId;
import com.petra.lib.block.ProcessValue;
import com.petra.lib.signal.SignalId;

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
