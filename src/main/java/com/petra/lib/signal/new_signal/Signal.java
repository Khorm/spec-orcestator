package com.petra.lib.signal.new_signal;

import com.petra.lib.signal.consumer.Receiver;
import com.petra.lib.signal.producer.Sender;
import com.petra.lib.variable.mapper.VariableMapper;
import com.petra.lib.variable.process.ProcessVariable;
import com.petra.lib.signal.model.ExecutionRequest;
import com.petra.lib.signal.model.ExecutionResponse;
import com.petra.lib.signal.model.Version;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.Collection;
import java.util.UUID;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@RequiredArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Signal {
    Receiver receiver;
    Sender sender;

    @EqualsAndHashCode.Include
    Long id;
    Version signalVersion;
    VariableMapper signalMapper;

    public Long getId(){
        return id;
    }

    public Version getVersion(){
        return signalVersion;
    }

    public void start(Consumer<ExecutionResponse> messageHandler){
        receiver.start(messageHandler);
    }

    public void send(UUID scenarioId, Collection<ProcessVariable> valueList,
                     BiConsumer<Exception, ExecutionRequest> sendErrorHandler) {
        Collection<ProcessVariable> signalValues = signalMapper.map(valueList);
        ExecutionRequest executionRequest = new ExecutionRequest(scenarioId, signalVersion, signalValues);
        sender.send(executionRequest, sendErrorHandler);
    }
}
