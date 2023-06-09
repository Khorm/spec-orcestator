package com.petra.lib.signal;

import com.petra.lib.manager.block.Block;
import com.petra.lib.signal.request.RequestSignal;
import com.petra.lib.signal.response.ResponseSignal;

public interface SignalMap {
    RequestSignal getRequestSignalById(SignalId signalId);
    ResponseSignal getResponseSignalById(SignalId signalId);
}
