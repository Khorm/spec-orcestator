package com.petra.lib.XXXXXXsignal.response.controller;

import com.petra.lib.block.VersionBlockId;
import com.petra.lib.state.variable.neww.ProcessValue;
import com.petra.lib.XXXXXXsignal.SignalId;

import java.util.Collection;
import java.util.UUID;

public interface ResponseReadyListener {
    void answerToRequest(Collection<ProcessValue> signalAnswerVariables,
                         SignalId signalId,
                         VersionBlockId requestBlockId,
                         UUID scenarioId,
                         VersionBlockId responseBlockId);

    void errorToRequest(SignalId signalId,
                        VersionBlockId requestBlockId,
                        UUID scenarioId,
                        VersionBlockId responseBlockId);

    void idempotencyErrorToRequest(Collection<ProcessValue> signalAnswerVariables,
                                  SignalId signalId,
                                  VersionBlockId requestBlockId,
                                  UUID scenarioId,
                                  VersionBlockId responseBlockId);
}
