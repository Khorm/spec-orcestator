package com.petra.lib.signal;

import com.petra.lib.manager.block.ProcessVariableDto;

import java.util.Collection;
import java.util.UUID;

public interface RequestSignal extends Signal {

    void send(Collection<ProcessVariableDto> senderVariables, UUID scenarioId);
    void setObserver(SignalRequestListener signalRequestListener);
}
