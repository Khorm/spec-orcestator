package com.petra.lib.XXXXXXsignal.___new.connector.producer.bridge;

import com.petra.lib.environment.dto.ResponseSignal;

public interface ProducerHandler {
    void answer(ResponseSignal responseSignal);
    void error();
}
