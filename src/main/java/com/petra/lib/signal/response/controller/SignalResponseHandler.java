package com.petra.lib.signal.response.controller;

import com.petra.lib.signal.SignalId;
import com.petra.lib.signal.dto.RequestDto;
import com.petra.lib.signal.response.ResponseSignal;


public interface SignalResponseHandler {
    void handleSignal(RequestDto requestDto, ResponseSignal signal);
}
