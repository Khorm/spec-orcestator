package com.petra.lib.signal.consumer;

import java.util.function.Consumer;

public interface Receiver {

    void start();
    void setHandler(Consumer<String> messageHandler);

}
