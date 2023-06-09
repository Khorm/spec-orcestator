package com.petra.lib.signal.dto;

import com.petra.lib.manager.block.BlockId;
import com.petra.lib.manager.block.ProcessVariableDto;
import com.petra.lib.signal.SignalId;
import com.petra.lib.signal.model.Version;
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
    SignalId signalVersion;
    Collection<ProcessVariableDto> signalVariables;
}
