package com.petra.lib.remote.output.http.answer;

import com.petra.lib.remote.output.http.AnswerDecoder;
import com.petra.lib.remote.output.http.SignalEncoder;
import com.petra.lib.remote.dto.AnswerDto;
import com.petra.lib.remote.signal.Signal;
import feign.Feign;

public class HttpAnswerOutputSocket implements OutputAnswerSocket {
    AnswerConsumer remoteEntryPointAnswer;


    public HttpAnswerOutputSocket(String serviceName){
        remoteEntryPointAnswer = Feign.builder()
                .encoder(new SignalEncoder())
                .decoder(new AnswerDecoder())
                .target(AnswerConsumer.class, "http://test/answer");
    }


    @Override
    public AnswerDto answer(Signal signal) {
        return remoteEntryPointAnswer.answer(signal);
    }
}
