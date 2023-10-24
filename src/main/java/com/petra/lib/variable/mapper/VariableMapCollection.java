package com.petra.lib.variable.mapper;

import com.petra.lib.variable.XXXXXfactory.VariableModel;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Коллекция хранит значения маппинга переменных.
 * Возвращает переменную консумера по айди переменной продюсера
 */
class VariableMapCollection {
    private final Map<Long, Long> producerConsumerMap = new HashMap<>();

    VariableMapCollection(Collection<VariableModel> variableModels){
        for (VariableModel variableModel : variableModels){
            if (variableModel.getVariableSources() == null) continue;
            for (Long sourceVariableId : variableModel.getVariableSources()){
                producerConsumerMap.put(sourceVariableId, variableModel.getId());
            }
        }
    }

    Long findConsumerVariableByProducerVariable(Long producerVariableId) {
        return producerConsumerMap.get(producerVariableId);
    }
}
