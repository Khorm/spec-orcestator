package com.petra.lib.environment.input;

import com.petra.lib.environment.dto.AnswerDto;
import com.petra.lib.environment.dto.Signal;

public interface WorkEnvironment {
    AnswerDto consume(Signal signal);
}
