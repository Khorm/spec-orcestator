package com.petra.lib.XXXXXXsignal.dto;

import com.petra.lib.block.BlockId;
import com.petra.lib.environment.context.ProcessValue;
import com.petra.lib.XXXXXXsignal.SignalId;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Collection;
import java.util.UUID;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Deprecated
public class RequestDto {
    UUID scenarioId;
    BlockId requestBlockId;
    SignalId signalId;
    Collection<ProcessValue> signalVariables;
}
