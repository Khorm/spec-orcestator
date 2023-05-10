package com.petra.lib.signal.model;

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
public class RequestDto {
    UUID scenarioId;
    Long signalId;
    Version signalVersion;
    Collection<ProcessVariableDto> signalVariables;
}
