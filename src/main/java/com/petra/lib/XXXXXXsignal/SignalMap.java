package com.petra.lib.XXXXXXsignal;

import com.petra.lib.XXXXXXsignal.request.RequestSignal;
import com.petra.lib.XXXXXXsignal.response.ResponseSignal;

public interface SignalMap {
    RequestSignal getRequestSignalById(SignalId signalId);
    ResponseSignal getResponseSignalById(SignalId signalId);
}
