package com.petra.lib.manager.block;

import com.petra.lib.signal.SignalRequestListener;
import com.petra.lib.signal.model.SignalTransferModel;
import lombok.Setter;

@Deprecated
class SignalRequestListenerWrapper implements SignalRequestListener {

    @Setter
    SignalRequestListener listener;

    @Override
    public void executed(SignalTransferModel signalTransferModel) {
        listener.executed(signalTransferModel);
    }

    @Override
    public void error(Exception e, SignalTransferModel signalTransferModel) {
        listener.error(e, signalTransferModel);
    }
}
