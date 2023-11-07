package com.petra.lib.XXXXXXsignal.___new.connector.producer.bridge;

import com.petra.lib.remote.signal.AnswerDto;
import com.petra.lib.remote.signal.ProducerSignalDto;
import feign.Headers;
import feign.RequestLine;

@Headers("Content-Type: application/json")
interface ConsumerEntryPoint {
    @RequestLine("POST")
    AnswerDto consume(ProducerSignalDto producerSignalDto);
}
