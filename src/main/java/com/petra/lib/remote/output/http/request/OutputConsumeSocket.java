package com.petra.lib.remote.output.http.request;

import com.petra.lib.remote.dto.AnswerDto;
import com.petra.lib.remote.signal.Signal;

/**
 * �����, ����������� ���� � ������� ������. ���������� ������������� ������.
 */
@Deprecated
interface OutputConsumeSocket {
    AnswerDto consume(Signal signal);
}
