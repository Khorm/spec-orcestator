package com.petra.lib.signal;

import com.petra.lib.variable.process.ProcessVariable;

import java.util.Collection;
import java.util.UUID;

public interface ResponseSignal extends Signal {

    void setAnswer(Collection<ProcessVariable> contextVariables, UUID scenarioId);
    void executionError(UUID scenarioId);
    void setListener(SignalResponseListener signalResponseListener);
}
