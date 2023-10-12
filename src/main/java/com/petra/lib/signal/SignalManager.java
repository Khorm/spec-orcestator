package com.petra.lib.signal;

import com.petra.lib.signal.dto.RequestDto;
import com.petra.lib.signal.dto.ResponseDto;

public interface SignalManager {

    /**
     * ��������� ����� �������.
     * @param requestDto
     * @return
     */
    ResponseDto syncRequest(RequestDto requestDto);

    /**
     * �������� ������ ��� �������� ������.
     * @param requestDto
     */
    void asyncRequest(RequestDto requestDto);
}
