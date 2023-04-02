package com.petra.lib.signal;

import com.petra.lib.signal.model.SignalTransferModel;

public interface SignalListener {
    void executeSignal(SignalTransferModel request);
}
