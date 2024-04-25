package com.petra.lib.remote.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Getter
public class SourceRequestDto {
    UUID scenarioId;
    Long sourceId;
    String sourceVariables;
    Long workflowId;
    String requestServiceName;
    Long requestBlockId;
}
