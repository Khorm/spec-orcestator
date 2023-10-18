package com.petra.lib.workflow.model;

import com.petra.lib.XXXXXXsignal.model.Version;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.Set;
import java.util.UUID;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Getter
public class RollbackRequest {
    UUID scenarioId;
    Long actionId;
    Set<Long> rollbackTransactionList;
    Version version;
}
