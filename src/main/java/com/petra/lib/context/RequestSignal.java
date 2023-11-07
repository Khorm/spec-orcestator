package com.petra.lib.context;

import com.petra.lib.block.VersionId;
import com.petra.lib.context.variables.VariablesContainer;
import com.petra.lib.remote.signal.SignalType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
class RequestSignal {
    VersionId versionId;
    String signalName;
    VariablesContainer signalContainer;
    SignalType signalType;
}
