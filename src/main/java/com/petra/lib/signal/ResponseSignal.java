package com.petra.lib.signal;

import com.petra.lib.manager.block.ProcessVariableDto;

import java.util.Collection;
import java.util.UUID;

public interface ResponseSignal extends Signal {

    void setAnswer(Collection<ProcessVariableDto> contextVariables, UUID scenarioId);
    void setError(UUID scenarioId);
    
    void setListener(SignalResponseListener signalResponseListener);
}
