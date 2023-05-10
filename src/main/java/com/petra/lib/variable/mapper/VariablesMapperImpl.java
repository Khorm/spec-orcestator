package com.petra.lib.variable.mapper;

import com.petra.lib.manager.block.ProcessVariableDto;
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
    public Collection<ProcessVariableDto> map(Collection<ProcessVariableDto> producerVariableCollection) {
        Collection<ProcessVariableDto> consumerVariables = new ArrayList<>();
        for (ProcessVariableDto producerVariable : producerVariableCollection) {
            ProcessVariableDto consumerProcessVariableDto = map(producerVariable);
            if (consumerProcessVariableDto == null) continue;
            consumerVariables.add(consumerProcessVariableDto);
        }
        return consumerVariables;
    }

    @Override
    public ProcessVariableDto map(ProcessVariableDto producerVariable) {
        Long consumerVariableId = consumerVariableCollection.findConsumerVariableByProducerVariable(producerVariable.getId());
        if (consumerVariableId == null) return null;
        return new ProcessVariableDto(consumerVariableId, producerVariable.getValue());
    }

}
