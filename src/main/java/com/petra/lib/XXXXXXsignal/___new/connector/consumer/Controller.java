package com.petra.lib.XXXXXXsignal.___new.connector.consumer;

import com.petra.lib.XXXXXXsignal.___new.connector.producer.bridge.ConsumerEntryPoint;
import com.petra.lib.remote.output.http.AnswerDecoder;
import com.petra.lib.remote.output.http.SignalEncoder;
import feign.Feign;

public class Controller {
    /**
     * Feigin interface client
     */
    ConsumerEntryPoint consumerEntryPoint;

    Controller(String serviceName){
        consumerEntryPoint = Feign.builder()
                .encoder(new SignalEncoder())
                .decoder(new AnswerDecoder())
                .target(ConsumerEntryPoint.class, "/" + serviceName);
    }
}
