package com.petra.lib.remote.output;

import com.petra.lib.block.VersionId;
import com.petra.lib.context.ActivityContext;
import com.petra.lib.context.variables.VariablesContainer;
import com.petra.lib.remote.signal.AnswerDto;
import com.petra.lib.remote.signal.Signal;
import com.petra.lib.remote.signal.SignalType;

import java.util.UUID;

/**
 * Сокет, свзфывающий блок с внешним блоком. Отправляет запрашивающий сигнал.
 */
public interface OutputConsumeSocket {
    AnswerDto consume(Signal signal);
}
