package com.petra.lib.signal.model;

import com.petra.lib.signal.SignalType;
import com.petra.lib.variable.process.ProcessVariable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.Collection;
import java.util.UUID;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Getter
@RequiredArgsConstructor
public class SignalTransferModel {
    Collection<ProcessVariable> signalVariables;
    Version version;
    Long signalId;
    UUID scenarioId;
    Long senderBlockId;
    SignalType signalType;
}
