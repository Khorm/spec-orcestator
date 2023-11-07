package com.petra.lib.XXXXXXsignal.request;

import com.petra.lib.context.variables.ProcessValue;
import com.petra.lib.XXXXXXsignal.Signal;
import com.petra.lib.XXXXXXsignal.model.Version;

import java.util.Collection;
import java.util.UUID;

@Deprecated
public interface RequestSignal extends Signal {
    void request(Collection<ProcessValue> signalVariables, UUID scenarioId, Long requestBlockId, Version blockVersion);
}
