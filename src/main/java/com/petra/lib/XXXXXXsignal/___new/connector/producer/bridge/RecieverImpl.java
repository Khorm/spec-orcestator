package com.petra.lib.XXXXXXsignal.___new.connector.producer.bridge;

import com.petra.lib.environment.dto.AnswerDto;
import com.petra.lib.environment.dto.ProducerSignalDto;
import com.petra.lib.environment.output.enums.SignalResult;
import com.petra.lib.environment.output.http.AnswerDecoder;
import com.petra.lib.environment.output.http.SignalEncoder;
import feign.Feign;

public class RecieverImpl implements Reciever {

    ConsumerEntryPoint consumerEntryPoint;

    public RecieverImpl(String consumerName){
        Feign.builder()
                .encoder(new SignalEncoder())
                .decoder(new AnswerDecoder())
                .target(ConsumerEntryPoint.class, "/" + consumerName + ":9292");
    }


    @Override
    public void recieve(ProducerSignalDto producerSignalDto, ProducerHandler producerHandler) {
        AnswerDto answerDto = consumerEntryPoint.consume(producerSignalDto);
        if(answerDto.getSignalResult() == SignalResult.ERROR){
            producerHandler.error();
        }
    }
}
