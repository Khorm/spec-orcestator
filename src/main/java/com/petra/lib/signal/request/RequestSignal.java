package com.petra.lib.signal.request;

import com.petra.lib.block.ProcessValue;
import com.petra.lib.signal.Signal;
import com.petra.lib.signal.model.Version;

import java.util.Collection;
import java.util.UUID;

public interface RequestSignal extends Signal {
    void request(Collection<ProcessValue> signalVariables, UUID scenarioId, Long requestBlockId, Version blockVersion);
}
