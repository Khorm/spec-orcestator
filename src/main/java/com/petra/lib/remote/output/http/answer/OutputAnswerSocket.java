package com.petra.lib.remote.output.http.answer;

import com.petra.lib.remote.dto.AnswerDto;
import com.petra.lib.remote.signal.Signal;

/**
 * Сокет, отвеающий на удаленное запрашивающее сообщение
 */
@Deprecated
interface OutputAnswerSocket {
    AnswerDto answer(Signal signal);
}
