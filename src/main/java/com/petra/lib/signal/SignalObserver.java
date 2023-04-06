package com.petra.lib.signal;

import com.petra.lib.signal.model.SignalTransferModel;

/**
 * Интерфейс слушателя сигнала
 */
public interface SignalObserver {
    void executed(SignalTransferModel signalTransferModel);
    void error(Exception e, SignalTransferModel signalTransferModel);
}
