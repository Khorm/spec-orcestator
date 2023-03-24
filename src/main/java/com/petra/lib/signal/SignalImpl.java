package com.petra.lib.signal;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.petra.lib.signal.consumer.Receiver;
import com.petra.lib.signal.model.SignalTransferModel;
import com.petra.lib.signal.model.Version;
import com.petra.lib.signal.producer.Sender;
import com.petra.lib.variable.mapper.VariableMapper;
import com.petra.lib.variable.process.ProcessVariable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.Collection;
import java.util.UUID;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
class SignalImpl implements Signal {
    Receiver receiver;
    Sender sender;

    @EqualsAndHashCode.Include
    Long id;
    Version signalVersion;
    VariableMapper signalMapper;
    Consumer<SignalTransferModel> executionHandler;


    public void send(UUID scenarioId, Collection<ProcessVariable> valueList,
                     BiConsumer<Exception, SignalTransferModel> sendErrorHandler) {
        Collection<ProcessVariable> signalValues = signalMapper.map(valueList);
        SignalTransferModel signalTransferModel = new SignalTransferModel(signalValues, signalVersion, id, scenarioId);
        sender.send(signalTransferModel, sendErrorHandler);
    }

    public void accept() {
        receiver.accept();
    }

    private void receive(String message) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            SignalTransferModel signalTransferModel = objectMapper.readValue(message, SignalTransferModel.class);
            executionHandler.accept(signalTransferModel);
//            threadManager.executeAndWait(() -> executionHandler.accept(signalModel));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
