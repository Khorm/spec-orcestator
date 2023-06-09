package com.petra.lib.signal.request.controller;


import com.petra.lib.signal.dto.RequestDto;
import com.petra.lib.signal.dto.ResponseDto;

public interface RequestHandler {
    void executed(ResponseDto responseDto);
    void error(Exception e, RequestDto responseDto);
}
