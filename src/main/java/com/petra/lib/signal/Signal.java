package com.petra.lib.signal;

import com.petra.lib.manager.ExecutionContext;
import com.petra.lib.variable.process.ProcessVariable;

import java.util.Collection;
import java.util.UUID;

public interface Signal {

    Long getId();
    void start();

//    void send(ExecutionContext executionContext, Collection<ProcessVariable> valueList);
//
//    void executeSignal();
//    void setCallback(Consumer<SignalTransferModel> executionHandler, BiConsumer<Exception, SignalTransferModel> sendErrorHandler);
}
