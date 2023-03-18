package com.petra.lib.signal.consumer;

import com.petra.lib.signal.model.ExecutionResponse;

import java.util.function.Consumer;

public interface Receiver {
    void start(Consumer<String> messageHandler);
    void accept();

}
