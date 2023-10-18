package com.petra.lib.environment.input;

import com.petra.lib.environment.dto.Signal;

public interface WorkEnvironment {
    void consume(Signal signal);
}
