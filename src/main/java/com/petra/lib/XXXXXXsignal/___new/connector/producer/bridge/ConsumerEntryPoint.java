package com.petra.lib.XXXXXXsignal.___new.connector.producer.bridge;

import com.petra.lib.environment.dto.AnswerDto;
import com.petra.lib.environment.dto.ProducerSignalDto;
import feign.Headers;
import feign.RequestLine;

@Headers("Content-Type: application/json")
interface ConsumerEntryPoint {
    @RequestLine("POST")
    AnswerDto consume(ProducerSignalDto producerSignalDto);
}
