package com.petra.lib.action.new_action;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.UUID;
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class ExecutionContextEntity {
    String valuesJson;
    UUID scenarioId;
    Long blockId;
    ExecutionResult executionResult;
}
