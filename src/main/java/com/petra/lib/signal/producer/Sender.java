package com.petra.lib.signal.producer;

import com.petra.lib.signal.model.ExecutionRequest;

import java.util.function.BiConsumer;

public interface Sender {

    void send(ExecutionRequest executionRequest, BiConsumer<Exception, ExecutionRequest> sendErrorHandler);
}
