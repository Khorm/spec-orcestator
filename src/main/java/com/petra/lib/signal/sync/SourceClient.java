package com.petra.lib.signal.sync;

import com.petra.lib.signal.model.SignalTransferModel;

public interface SourceClient {

    SignalTransferModel getSource(SignalTransferModel signalModel);
}
