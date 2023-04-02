package com.petra.lib.signal;

import com.petra.lib.signal.model.SignalTransferModel;

import java.util.function.BiConsumer;

@Deprecated
public interface Sender {

    void send(SignalTransferModel executionRequest);
}
