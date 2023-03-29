package com.petra.lib.signal.producer;

import com.petra.lib.signal.model.SignalTransferModel;

import java.util.function.BiConsumer;

public interface Sender {

    void send(SignalTransferModel executionRequest, BiConsumer<Exception, SignalTransferModel> sendErrorHandler);
}
