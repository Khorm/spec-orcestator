package com.petra.lib.XXXXXXsignal;

import com.petra.lib.XXXXXXsignal.dto.RequestDto;
import com.petra.lib.XXXXXXsignal.dto.ResponseDto;

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
