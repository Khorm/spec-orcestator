package com.petra.lib.signal;

import com.petra.lib.signal.model.SignalTransferModel;

public interface SignalResponseListener {
    void executeSignal(SignalTransferModel request);
}
