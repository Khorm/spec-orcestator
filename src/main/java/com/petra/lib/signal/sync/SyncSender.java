package com.petra.lib.signal.sync;

import com.petra.lib.manager.ExecutionContext;
import com.petra.lib.signal.SenderSignal;
import com.petra.lib.signal.SignalObserver;
import com.petra.lib.signal.SignalType;
import com.petra.lib.signal.model.SignalTransferModel;
import com.petra.lib.signal.model.Version;
import com.petra.lib.variable.mapper.VariableMapper;
import com.petra.lib.variable.process.ProcessVariable;
import feign.Feign;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

import java.util.Collection;
import java.util.UUID;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SyncSender implements SenderSignal {

    SourceClient sourceClient;
    SignalObserver messageHandler;
    VariableMapper signalMapper;
    Version version;
    Long signalId;

    SyncSender(String URL, SignalObserver messageHandler, VariableMapper signalMapper, Version version, Long signalId) {
        sourceClient = Feign.builder()
                .encoder(new SignalEncoder())
                .decoder(new SignalDecoder())
                .target(SourceClient.class, URL);
        this.messageHandler = messageHandler;
        this.signalMapper = signalMapper;
        this.version = version;
        this.signalId = signalId;
    }


    @Override
    public Long getId() {
        return null;
    }

    @Override
    public void start() {

    }

    @Override
    public void send(Collection<ProcessVariable> senderVariables, UUID scenarioId) {
        Collection<ProcessVariable> signalVariables = signalMapper.map(senderVariables);
        SignalTransferModel request = new SignalTransferModel(signalVariables, version, signalId, scenarioId, SignalType.REQUEST);
        SignalTransferModel result;
        try {
            result = sourceClient.getSource(request);
        } catch (Exception e) {
            messageHandler.error(e, request);
            return;
        }
        messageHandler.executed(result);
    }

}
