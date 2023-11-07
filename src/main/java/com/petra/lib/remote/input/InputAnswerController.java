package com.petra.lib.remote.input;

import com.petra.lib.remote.signal.AnswerDto;
import com.petra.lib.remote.signal.Signal;

public interface InputAnswerController {
    SignalResult answer(Signal signal);
}
