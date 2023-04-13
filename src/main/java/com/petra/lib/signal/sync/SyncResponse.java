package com.petra.lib.signal.sync;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.petra.lib.manager.block.ThreadManager;
import com.petra.lib.signal.ResponseSignal;
import com.petra.lib.signal.SignalListener;
import com.petra.lib.signal.SignalType;
import com.petra.lib.signal.model.SignalTransferModel;
import com.petra.lib.signal.model.Version;
import com.petra.lib.variable.process.ProcessVariable;
import fi.iki.elonen.NanoHTTPD;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.UUID;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SyncResponse extends NanoHTTPD implements ResponseSignal {

    SignalListener handler;
    /**
     * Асинхронный хранитель ответов для требования при ответе
     */
    AnswersMap answersMap = new AnswersMap();
    Version answerVersion;
    Long signalId;
    Long blockId;
    ObjectMapper objectMapper = new ObjectMapper();

    public SyncResponse(String hostname, int port, SignalListener handler,
                        Long blockId, Version answerVersion,
                        Long signalId) {
        super(hostname, port);
        this.handler = handler;
        this.answerVersion = answerVersion;
        this.signalId = signalId;
        this.blockId = blockId;
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

                ThreadManager.execute(() -> handler.executeSignal(signalTransferModel));
                answersMap.waitAnswer(signalTransferModel.getScenarioId());

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
    public Long getId() {
        return signalId;
    }

    @Override
    public void startSignal() {
        //doNothin
    }

    @Override
    public void setAnswer(Collection<ProcessVariable> contextVariables, UUID scenarioId) {
        SignalTransferModel signalTransferModel = new SignalTransferModel(
                contextVariables,
                answerVersion,
                signalId,
                scenarioId,
                blockId,
                SignalType.RESPONSE
        );
        answersMap.putOnlyIfExists(signalTransferModel);
    }

    @Override
    public void executionError(UUID scenarioId) {
        SignalTransferModel errorTransferModel = new SignalTransferModel(
                null,
                answerVersion,
                signalId,
                scenarioId,
                signalId,
                SignalType.ERROR
        );
        answersMap.putOnlyIfExists(errorTransferModel);
    }
}
