package com.petra.lib.remote.output.http.request;

import com.petra.lib.remote.output.http.AnswerDecoder;
import com.petra.lib.remote.output.http.SignalEncoder;
import com.petra.lib.remote.dto.AnswerDto;
import com.petra.lib.remote.signal.Signal;
import feign.Feign;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

/**
 * не синглтоновый класс обрабатывающий каждый свой конкретный запрос
 */
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
class HttpOutputSocket implements OutputConsumeSocket {
    ExecutionConsumer remoteEntryPointRequest;

    public HttpOutputSocket(String serviceName) {
        remoteEntryPointRequest = Feign.builder()
                .encoder(new SignalEncoder())
                .decoder(new AnswerDecoder())
                .target(ExecutionConsumer.class, "http://" + serviceName);
    }

    @Override
    public AnswerDto consume(Signal signal) {
        return remoteEntryPointRequest.execute(signal);
    }

}
