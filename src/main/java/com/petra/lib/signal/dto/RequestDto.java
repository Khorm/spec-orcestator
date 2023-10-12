package com.petra.lib.signal.dto;

import com.petra.lib.block.BlockId;
import com.petra.lib.block.ProcessValue;
import com.petra.lib.signal.SignalId;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Collection;
import java.util.UUID;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RequestDto {
    UUID scenarioId;
    BlockId requestBlockId;
    SignalId signalId;
    Collection<ProcessValue> signalVariables;
}
