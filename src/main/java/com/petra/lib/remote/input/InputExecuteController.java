package com.petra.lib.remote.input;

import com.petra.lib.remote.dto.AnswerDto;
import com.petra.lib.remote.dto.SignalDTO;

public interface InputExecuteController {
    AnswerDto execute(SignalDTO signal);
}
