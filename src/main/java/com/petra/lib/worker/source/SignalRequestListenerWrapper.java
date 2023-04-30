package com.petra.lib.worker.source;

import com.petra.lib.signal.SignalRequestListener;
import com.petra.lib.signal.model.SignalTransferModel;
import lombok.Setter;


public class SignalRequestListenerWrapper implements SignalRequestListener {

    @Setter
    private SignalRequestListener listener;

    @Override
    public void executed(SignalTransferModel signalTransferModel) {
        listener.executed(signalTransferModel);
    }

    @Override
    public void error(Exception e, SignalTransferModel signalTransferModel) {
        listener.error(e, signalTransferModel);
    }
}
