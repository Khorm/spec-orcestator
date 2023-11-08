package com.petra.lib.remote.output;

import com.petra.lib.remote.signal.AnswerDto;
import com.petra.lib.remote.signal.Signal;

/**
 * Сокет, свзфывающий блок с внешним блоком. Отправляет запрашивающий сигнал.
 */
public interface OutputConsumeSocket {
    AnswerDto consume(Signal signal);
}
