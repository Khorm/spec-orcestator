package com.petra.lib.signal;

import com.petra.lib.signal.dto.RequestDto;
import com.petra.lib.signal.dto.ResponseDto;

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
