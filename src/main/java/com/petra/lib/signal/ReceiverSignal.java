package com.petra.lib.signal;

import com.petra.lib.signal.model.SignalTransferModel;

import java.util.UUID;

public interface ReceiverSignal extends Signal{

    void setAnswer(SignalTransferModel answer);
    void executionError(SignalTransferModel executingRequest);
}
