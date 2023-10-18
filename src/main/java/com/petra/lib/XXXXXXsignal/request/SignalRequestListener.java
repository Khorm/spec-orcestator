package com.petra.lib.XXXXXXsignal.request;


import com.petra.lib.XXXXXXsignal.SignalId;
import com.petra.lib.XXXXXXsignal.dto.RequestDto;
import com.petra.lib.XXXXXXsignal.dto.ResponseDto;

/**
 * Signal listener interface
 */
public interface SignalRequestListener {
    void executed(ResponseDto responseDto, SignalId signalId);
    void error(Exception e, RequestDto responseDto, SignalId signalId);
}
