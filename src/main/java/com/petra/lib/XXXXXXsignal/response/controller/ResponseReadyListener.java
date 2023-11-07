package com.petra.lib.XXXXXXsignal.response.controller;

import com.petra.lib.block.VersionId;
import com.petra.lib.context.variables.ProcessValue;
import com.petra.lib.XXXXXXsignal.SignalId;

import java.util.Collection;
import java.util.UUID;

public interface ResponseReadyListener {
    void answerToRequest(Collection<ProcessValue> signalAnswerVariables,
                         SignalId signalId,
                         VersionId requestBlockId,
                         UUID scenarioId,
                         VersionId responseBlockId);

    void errorToRequest(SignalId signalId,
                        VersionId requestBlockId,
                        UUID scenarioId,
                        VersionId responseBlockId);

    void idempotencyErrorToRequest(Collection<ProcessValue> signalAnswerVariables,
                                  SignalId signalId,
                                  VersionId requestBlockId,
                                  UUID scenarioId,
                                  VersionId responseBlockId);
}
