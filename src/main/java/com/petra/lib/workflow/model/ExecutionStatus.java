package com.petra.lib.workflow.model;

import com.petra.lib.workflow.enums.ScenarioSignal;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Getter
public class ExecutionStatus {
    ScenarioSignal status;
    String error;
}
