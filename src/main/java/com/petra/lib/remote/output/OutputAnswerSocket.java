package com.petra.lib.remote.output;

import com.petra.lib.context.ActivityContext;
import com.petra.lib.context.variables.VariablesContainer;
import com.petra.lib.remote.signal.AnswerDto;
import com.petra.lib.remote.signal.Signal;
import com.petra.lib.remote.signal.SignalType;

/**
 * Сокет, отвеающий на удаленное запрашивающее сообщение
 */
public interface OutputAnswerSocket {
    AnswerDto answer(Signal signal);
}
