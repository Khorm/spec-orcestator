package com.petra.lib.XXXXXXsignal.request.controller;


import com.petra.lib.XXXXXXsignal.dto.RequestDto;
import com.petra.lib.XXXXXXsignal.dto.ResponseDto;

public interface RequestHandler {
    void executed(ResponseDto responseDto);
    void error(Exception e, RequestDto responseDto);
}
