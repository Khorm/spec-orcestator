package com.petra.lib.signal.async;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
class SignalImpl /*implements Signal*/ {
    /*Receiver receiver;
    Sender sender;

    @EqualsAndHashCode.Include
    Long id;
    Version signalVersion;
    VariableMapper signalMapper;

    SignalObserver observer;
//    BiConsumer<Exception, SignalTransferModel> sendErrorHandler;


    SignalImpl(Receiver receiver, Sender sender, Long id, Version signalVersion,
               VariableMapper signalMapper, SignalObserver observer) {
        this.receiver = receiver;
        this.receiver.setHandler(this::receive);
        this.sender = sender;
        this.id = id;
        this.signalVersion = signalVersion;
        this.signalMapper = signalMapper;
        this.observer = observer;
    }


    @Override
    public Long getId() {
        return id;
    }

    public void send(UUID scenarioId, Collection<ProcessVariable> valueList) {
        Collection<ProcessVariable> signalValues = signalMapper.map(valueList);
        SignalTransferModel signalTransferModel = new SignalTransferModel(signalValues, signalVersion, id, scenarioId);
        sender.send(signalTransferModel, observer::error);
    }

    @Override
    public void executeSignal() {
        receiver.release();
    }

    private void receive(String message) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            SignalTransferModel signalTransferModel = objectMapper.readValue(message, SignalTransferModel.class);
//            executionHandler.accept(signalTransferModel);
            observer.executed(signalTransferModel);
//            threadManager.executeAndWait(() -> executionHandler.accept(signalModel));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/

}
