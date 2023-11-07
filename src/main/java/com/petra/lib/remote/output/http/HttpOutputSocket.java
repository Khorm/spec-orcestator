package com.petra.lib.remote.output.http;

import com.petra.lib.remote.output.OutputAnswerSocket;
import com.petra.lib.remote.output.OutputConsumeSocket;
import com.petra.lib.remote.signal.AnswerDto;
import com.petra.lib.remote.signal.Signal;
import feign.Feign;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.net.URI;

/**
 * не синглтоновый класс обрабатывающий каждый свой конкретный запрос
 */
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
class HttpOutputSocket implements OutputConsumeSocket, OutputAnswerSocket {

//    /**
//     * айди отправляемого сигнала
//     */
//    VersionId signalId;
//
//    /**
//     * Имя отправляемого сигнала
//     */
//    String signalName;

//    /**
//     * Айди блока получателя
//     */
//    VersionId consumerBlockId;

    /**
     * имя сервиса получателя
     */
//    String consumerServiceName;
//    Long consumerServiceId;
//
//    Long producerServiceId;
//    String producerServiceName;


    ExecutionConsumer remoteEntryPointRequest = Feign.builder()
            .encoder(new SignalEncoder())
            .decoder(new AnswerDecoder())
            .target(ExecutionConsumer.class, "http://test");


    AnswerConsumer remoteEntryPointAnswer = Feign.builder()
            .encoder(new SignalEncoder())
            .decoder(new AnswerDecoder())
            .target(AnswerConsumer.class, "http://test/answer");


    @Override
    public AnswerDto consume(Signal signal){
        URI newURI = URI.create("http://"+signal.getConsumerService());
        return remoteEntryPointRequest.execute(newURI, signal);
    }

    @Override
    public AnswerDto answer(Signal signal) {
        URI newURI = URI.create("http://"+signal.getConsumerService()+"/answer");
        return remoteEntryPointAnswer.answer(newURI,signal);
    }
}
