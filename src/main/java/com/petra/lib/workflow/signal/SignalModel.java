package com.petra.lib.workflow.signal;

import com.petra.lib.variable.mapper.VariableMapper;
import com.petra.lib.variable.value.VariablesContainer;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

import java.util.Map;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
class SignalModel {
    Long signalId;

    /**
     * Хранит мапперы в переменные сигнала по айди продюсера сигнала
     */
    Map<Long, VariableMapper> sourceMapperByProducerBlockId;

    public Signal createSignal(Long producerBlockId, VariablesContainer producerVariableContainer){
        VariablesContainer sourceContainer = sourceMapperByProducerBlockId.get(producerBlockId).map(producerVariableContainer);
        return new Signal(signalId, sourceContainer);
    }


}
