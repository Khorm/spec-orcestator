package com.petra.lib.signal.sync;

import com.petra.lib.manager.thread.ThreadManager;
import com.petra.lib.signal.RequestSignal;
import com.petra.lib.signal.SignalRequestListener;
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
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class SyncRequest implements RequestSignal {

    SourceClient sourceClient;
    SignalRequestListener observer;
    VariableMapper signalMapper;
    Version version;
    Long signalId;
    Long blockId;
    static Executor requestExecutor = Executors.newCachedThreadPool();

    public SyncRequest(String URL, VariableMapper signalMapper,
                       Long blockId, Version version, Long signalId) {
        sourceClient = Feign.builder()
                .encoder(new SignalEncoder())
                .decoder(new SignalDecoder())
                .target(SourceClient.class, URL);
        this.signalMapper = signalMapper;
        this.version = version;
        this.signalId = signalId;
        this.blockId = blockId;
    }

    @Override
    public Long getId() {
        return signalId;
    }

    @Override
    public void startSignal() {

    }

    @Override
    public void send(Collection<ProcessVariable> senderVariables, UUID scenarioId) {
        Collection<ProcessVariable> signalVariables = signalMapper.map(senderVariables);
        SignalTransferModel request = new SignalTransferModel(scenarioId, signalId, version, blockId, SignalType.REQUEST, signalVariables);
        requestExecutor.execute(() -> {
            SignalTransferModel result;
            try {
                result = sourceClient.getSource(request);
                ThreadManager.execute(() -> observer.executed(result));
            } catch (Exception e) {
                ThreadManager.execute(() -> observer.error(e, request));
                return;
            }
        });

    }

    @Override
    public void setObserver(SignalRequestListener signalRequestListener) {
        this.observer = signalRequestListener;
    }

}
