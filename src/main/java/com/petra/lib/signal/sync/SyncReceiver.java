package com.petra.lib.signal.sync;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.petra.lib.signal.ReceiverSignal;
import com.petra.lib.signal.SignalListener;
import com.petra.lib.signal.model.SignalTransferModel;
import com.petra.lib.signal.model.Version;
import fi.iki.elonen.NanoHTTPD;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

import java.io.IOException;
import java.util.HashMap;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SyncReceiver extends NanoHTTPD implements ReceiverSignal {

    SignalListener handler;
    ObjectMapper objectMapper = new ObjectMapper();
    AnswersMap answersMap = new AnswersMap();
    Version version;
    Long signalId;

    public SyncReceiver(String hostname, int port, SignalListener handler, Version version, Long signalId) {
        super(hostname, port);
        this.handler = handler;
        this.version = version;
        this.signalId = signalId;
    }

    @Override
    public Response serve(IHTTPSession session) {
        if (session.getMethod() == Method.POST) {
            try {
                session.parseBody(new HashMap<>());
                String requestBody = session.getQueryParameterString();
                SignalTransferModel signalTransferModel = objectMapper.readValue(requestBody, SignalTransferModel.class);
                SignalTransferModel prevRequest = answersMap.addKey(signalTransferModel.getScenarioId());
                if (prevRequest != null) {
                    return newFixedLengthResponse(Response.Status.CONFLICT, MIME_PLAINTEXT, "Already executing");
                }
                handler.executeSignal(signalTransferModel);
                SignalTransferModel answer = answersMap.remove(signalTransferModel.getScenarioId());
                if (answer == null) {
                    return newFixedLengthResponse(Response.Status.INTERNAL_ERROR, MIME_PLAINTEXT, "Error");
                } else return newFixedLengthResponse(objectMapper.writeValueAsString(answer));
            } catch (IOException | ResponseException e) {
                // handle
            }
        }
        return newFixedLengthResponse(Response.Status.INTERNAL_ERROR, MIME_PLAINTEXT, "Error");
    }

    @Override
    public void setAnswer(SignalTransferModel signalTransferModel) {
        answersMap.putIfExists(signalTransferModel);
    }

    @Override
    public void executionError(SignalTransferModel executingRequest) {
        answersMap.remove(executingRequest.getScenarioId());
    }

    @Override
    public Long getId() {
        return signalId;
    }
}
