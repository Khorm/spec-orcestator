package com.petra.lib.signal.source;


import com.petra.lib.signal.new_signal.Signal;
import com.petra.lib.variable.process.ProcessVariable;
import com.petra.lib.signal.model.ExecutionRequest;
import com.petra.lib.signal.model.ExecutionResponse;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.Collection;
import java.util.Set;
import java.util.UUID;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class SourceSignal {
    @EqualsAndHashCode.Include
    Signal signal;

    @Getter
    Set<SourceSignal> parentSignals;

    @Getter
    Set<SourceSignal> childSignals;

    public void start(Consumer<ExecutionResponse> messageHandler){
        signal.start(messageHandler);
    }

    public void send(UUID scenarioId, Collection<ProcessVariable> valueList,
                     BiConsumer<Exception, ExecutionRequest> sendErrorHandler){
        signal.send(scenarioId, valueList,sendErrorHandler);
    }


}
