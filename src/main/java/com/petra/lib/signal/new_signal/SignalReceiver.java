package com.petra.lib.signal.new_signal;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.petra.lib.manager.ThreadManager;
import com.petra.lib.signal.consumer.Receiver;
import com.petra.lib.signal.model.SignalModel;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.function.Consumer;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class StarterSignal {
    Receiver receiver;
    Consumer<SignalModel> executionHandler;
    ThreadManager threadManager;

    public synchronized void start() {
        receiver.start(this::messageReceiveHandler);
    }

    private void messageReceiveHandler(String message) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            SignalModel signalModel = objectMapper.readValue(message, SignalModel.class);
            threadManager.executeAndWait(() -> executionHandler.accept(signalModel));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void accept(){
        receiver.accept();
    }
}
