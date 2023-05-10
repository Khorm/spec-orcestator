package com.petra.lib.signal;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface SignalResponseListener {
    void executeSignal(String message) throws JsonProcessingException, InterruptedException;
}
