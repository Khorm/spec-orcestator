package com.petra.lib.signal.queue;

import com.petra.lib.signal.dto.RequestDto;
import com.petra.lib.signal.dto.ResponseDto;
import com.petra.lib.signal.request.RequestSignal;

public interface QueueHandler {
    void executeSignal(RequestDto requestDto) throws Exception;
}
