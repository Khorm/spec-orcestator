package com.petra.lib.signal.request;

import com.petra.lib.signal.dto.RequestDto;
import com.petra.lib.signal.dto.ResponseDto;
import feign.Headers;
import feign.RequestLine;

@Headers("Content-Type: application/json")
public interface SourceClient {
    @RequestLine("POST")
    ResponseDto getSource(RequestDto requestDto);
}
