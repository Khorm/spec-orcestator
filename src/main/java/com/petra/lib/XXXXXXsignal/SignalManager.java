package com.petra.lib.XXXXXXsignal;

import com.petra.lib.XXXXXXsignal.dto.RequestDto;
import com.petra.lib.XXXXXXsignal.dto.ResponseDto;

public interface SignalManager {

    /**
     * Запросить вызов смгнала.
     * @param requestDto
     * @return
     */
    ResponseDto syncRequest(RequestDto requestDto);

    /**
     * Запросиь сигнал без ожидания ответа.
     * @param requestDto
     */
    void asyncRequest(RequestDto requestDto);
}
