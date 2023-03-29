package com.petra.lib.signal;

import com.petra.lib.signal.model.SignalTransferModel;
import com.petra.lib.variable.process.ProcessVariable;

import java.util.Collection;
import java.util.UUID;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public interface Signal {

    Long getId();
    void send(UUID scenarioId, Collection<ProcessVariable> valueList);
//    void setCallback(Consumer<SignalTransferModel> executionHandler, BiConsumer<Exception, SignalTransferModel> sendErrorHandler);
}
