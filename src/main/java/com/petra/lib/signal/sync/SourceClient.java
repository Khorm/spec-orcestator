package com.petra.lib.signal.sync;

import com.petra.lib.signal.model.SignalTransferModel;
import feign.Headers;
import feign.RequestLine;

@Headers("Content-Type: application/json")
public interface SourceClient {
    @RequestLine("POST")
    SignalTransferModel getSource(SignalTransferModel signalModel);
}
