package com.petra.lib.signal.request;

import com.petra.lib.manager.block.ProcessVariableDto;
import com.petra.lib.signal.Signal;
import com.petra.lib.signal.model.Version;
import com.petra.lib.signal.request.SignalRequestListener;

import java.util.Collection;
import java.util.UUID;

public interface RequestSignal extends Signal {
    void request(Collection<ProcessVariableDto> signalVariables, UUID scenarioId, Long requestBlockId, Version blockVersion);
}
