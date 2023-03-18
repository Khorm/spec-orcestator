package com.petra.lib.action.model;

import com.petra.lib.workflow.model.ValueList;
import com.petra.lib.signal.model.Version;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Getter
public class RollbackModel {
    UUID scenarioId;
    Long actionId;
    ValueList rollbackValues;
    Version version;
}
