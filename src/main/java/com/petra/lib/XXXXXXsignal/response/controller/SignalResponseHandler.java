package com.petra.lib.XXXXXXsignal.response.controller;

import com.petra.lib.XXXXXXsignal.dto.RequestDto;
import com.petra.lib.XXXXXXsignal.response.ResponseSignal;


public interface SignalResponseHandler {
    void handleSignal(RequestDto requestDto, ResponseSignal signal);
}
