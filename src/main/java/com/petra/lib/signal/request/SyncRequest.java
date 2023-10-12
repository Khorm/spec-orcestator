package com.petra.lib.signal.request;

import com.petra.lib.block.ProcessValue;
import com.petra.lib.signal.SignalId;
import com.petra.lib.signal.dto.RequestDto;
import com.petra.lib.signal.dto.ResponseDto;
import com.petra.lib.signal.model.SignalModel;
import com.petra.lib.signal.model.Version;
import com.petra.lib.signal.response.http.ResponseSignalDecoder;
import com.petra.lib.signal.response.http.SignalEncoder;
import feign.Feign;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;

import java.util.Collection;
import java.util.UUID;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Http signal request handler
 */
@Log4j2
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SyncRequest implements RequestSignal {

    /**
     * Feigin interface client
     */
    SourceClient sourceClient;

    /**
     * Signal ID
     */
    SignalId signalId;

    /**
     * The bulkhead for sync http request
     */
    static Executor bulkhead = Executors.newCachedThreadPool();

    /**
     * Request answer listener
     */
    SignalRequestListener listener;

    /**
     * Signal name
     */
    String signalName;

    public SyncRequest(SignalModel signalModel, SignalRequestListener listener) {
        sourceClient = Feign.builder()
                .encoder(new SignalEncoder())
                .decoder(new ResponseSignalDecoder())
                .target(SourceClient.class, "/" + signalModel.getServiceName() + "/petra/" + signalModel.getSignalName()
                        + "/" + signalModel.getVersion().getVersionName());
        this.signalId = new SignalId(signalModel.getSignalId(), signalModel.getVersion());
        this.signalName = signalModel.getSignalName();
        this.listener = listener;
    }

    @Override
    public SignalId getId() {
        return signalId;
    }

    @Override
    public void startSignal() {

    }


    @Override
    public void request(Collection<ProcessValue> signalVariables, UUID scenarioId, Long requestBlockId, Version blockVersion) {
        RequestDto requestDto = new RequestDto(
                scenarioId,
                requestBlockId,
                blockVersion,
                signalVariables
        );
        bulkhead.execute(() -> {
            try {
                ResponseDto responseDto = sourceClient.getSource(requestDto);
                listener.executed(responseDto, signalId);
            } catch (Exception e) {
                log.error(e);
                listener.error(e, requestDto, signalId);
            }
        });
    }
}
