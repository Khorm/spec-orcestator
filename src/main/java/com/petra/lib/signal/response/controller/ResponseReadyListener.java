package com.petra.lib.signal.response.controller;

import com.petra.lib.manager.block.BlockId;
import com.petra.lib.manager.block.ProcessVariableDto;
import com.petra.lib.signal.SignalId;

import java.util.Collection;
import java.util.UUID;

public interface ResponseReadyListener {
    void answerToRequest(Collection<ProcessVariableDto> signalAnswerVariables,
                         SignalId signalId,
                         BlockId requestBlockId,
                         UUID scenarioId,
                         BlockId responseBlockId);

    void errorToRequest(SignalId signalId,
                        BlockId requestBlockId,
                        UUID scenarioId,
                        BlockId responseBlockId);

    void idempotencyErrorToRequest(Collection<ProcessVariableDto> signalAnswerVariables,
                                  SignalId signalId,
                                  BlockId requestBlockId,
                                  UUID scenarioId,
                                  BlockId responseBlockId);
}
