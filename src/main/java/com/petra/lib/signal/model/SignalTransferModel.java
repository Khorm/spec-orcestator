package com.petra.lib.signal.model;

import com.petra.lib.signal.SignalType;
import com.petra.lib.manager.block.ProcessVariableDto;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Collection;
import java.util.UUID;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SignalTransferModel {
    UUID scenarioId;
    Long signalId;
    Version version;
    Long senderBlockId;
    SignalType signalType;
    Collection<ProcessVariableDto> signalVariables;
}
