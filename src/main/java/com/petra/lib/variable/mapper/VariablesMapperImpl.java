package com.petra.lib.variable.mapper;

import com.petra.lib.context.value.ProcessValue;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Класс непосредственно переделывающий входящую коллекцию переменных в исходящую
 */

@RequiredArgsConstructor
class VariablesMapperImpl implements VariableMapper{

    /**
     * Коллекция переменых владельца
     */
    private final VariableMapCollection consumerVariableCollection;

    @Override
    public Collection<ProcessValue> map(Collection<ProcessValue> producerVariableCollection) {
        Collection<ProcessValue> consumerVariables = new ArrayList<>();
        for (ProcessValue producerVariable : producerVariableCollection) {
            ProcessValue consumerProcessValue = map(producerVariable);
            if (consumerProcessValue == null) continue;
            consumerVariables.add(consumerProcessValue);
        }
        return consumerVariables;
    }

    @Override
    public ProcessValue map(ProcessValue producerVariable) {
        Long consumerVariableId = consumerVariableCollection.findConsumerVariableByProducerVariable(producerVariable.getId());
        if (consumerVariableId == null) return null;
        return new ProcessValue(consumerVariableId, producerVariable.getValue(), name, loaded);
    }

}
