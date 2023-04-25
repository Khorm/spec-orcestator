package com.petra.lib.signal;

import com.petra.lib.variable.process.ProcessVariable;

import java.util.Collection;
import java.util.UUID;

public interface RequestSignal extends Signal {

    void send(Collection<ProcessVariable> senderVariables, UUID scenarioId);
    void setObserver(SignalRequestListener signalRequestListener);
}
