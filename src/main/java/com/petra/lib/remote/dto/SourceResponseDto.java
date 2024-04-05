package com.petra.lib.remote.dto;

import com.petra.lib.source.enums.SourceResultStatus;
import com.petra.lib.variable.value.VariablesContainer;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Getter
public class SourceResponseDto {
    UUID scenarioId;
    Long sourceId;
    VariablesContainer signalVariables;
    SourceResultStatus status;
}
