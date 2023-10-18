package com.petra.lib.XXXXXXsignal.___new.connector.producer;

import com.petra.lib.XXXXXXsignal.___new.connector.producer.bridge.ConsumerEntryPoint;
import com.petra.lib.XXXXXXsignal.dto.ResponseDto;
import com.petra.lib.XXXXXXsignal.model.SignalModel;
import com.petra.lib.XXXXXXsignal.request.RequestSignal;
import com.petra.lib.XXXXXXsignal.request.SignalRequestListener;
import com.petra.lib.environment.output.http.AnswerDecoder;
import com.petra.lib.environment.output.http.SignalEncoder;
import feign.Feign;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Http signal request handler
 */
@Log4j2
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SyncRequest /*implements RequestSignal */{

    /**
     * Feigin interface client
     */
    ConsumerEntryPoint consumerEntryPoint;

    /**
     * Signal ID
     */
//    SignalId signalId;

    /**
     * The bulkhead for sync http request
     */
    static Executor bulkhead = Executors.newCachedThreadPool();

//    /**
//     * Request answer listener
//     */
//    SignalRequestListener listener;

    /**
     * Signal name
     */
//    String signalName;

    public SyncRequest(SignalModel signalModel, SignalRequestListener listener) {
        consumerEntryPoint = Feign.builder()
                .encoder(new SignalEncoder())
                .decoder(new AnswerDecoder())
                .target(ConsumerEntryPoint.class, "/" + signalModel.getServiceName());
//        this.signalName = signalModel.getSignalName();

    }

//    @Override
//    public SignalId getId() {
//        return signalId;
//    }


    @Override
    public void request(RequestSignal requestSignal) {
        bulkhead.execute(() -> {
            try {
                ResponseDto responseDto = consumerEntryPoint.getAnswer(requestSignal);
                listener.executed(responseDto, signalId);
            } catch (Exception e) {
                log.error(e);
                listener.error(e, requestDto, signalId);
            }
        });
    }
}
