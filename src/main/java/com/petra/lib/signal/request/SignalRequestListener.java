package com.petra.lib.signal.request;


import com.petra.lib.signal.SignalId;
import com.petra.lib.signal.dto.RequestDto;
import com.petra.lib.signal.dto.ResponseDto;

/**
 * Signal listener interface
 */
public interface SignalRequestListener {
    void executed(ResponseDto responseDto, SignalId signalId);
    void error(Exception e, RequestDto responseDto, SignalId signalId);
}
