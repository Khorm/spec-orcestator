package com.petra.lib.manager.factory;

import com.petra.lib.signal.SignalObserver;
import com.petra.lib.signal.model.SignalTransferModel;
import lombok.Setter;

class SignalObserverWrapper implements SignalObserver {

    @Setter
    SignalObserver listener;

    @Override
    public void executed(SignalTransferModel signalTransferModel) {
        listener.executed(signalTransferModel);
    }

    @Override
    public void error(Exception e, SignalTransferModel signalTransferModel) {
        listener.error(e, signalTransferModel);
    }
}
