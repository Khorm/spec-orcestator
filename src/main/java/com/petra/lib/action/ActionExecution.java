package com.petra.lib.action;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.UUID;

@Deprecated
@Getter
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class ActionExecution {
    UUID scenarioId;
    Long actionId;
    LocalDateTime executionTime;
    String resultValues;
}
