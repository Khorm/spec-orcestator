package com.petra.lib.variable.mapper;

import com.petra.lib.variable.factory.VariableModel;
import lombok.RequiredArgsConstructor;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 * Коллекция хранит значения маппинга переменных.
 * Возвращает переменную консумера по айди переменной продюсера
 */
class VariableMapCollection {
    private final Map<Long, Long> producerConsumerMap = new HashMap<>();

    VariableMapCollection(Collection<VariableModel> variableModels){
        for (VariableModel variableModel : variableModels){
            for (Long sourceVariableId : variableModel.getVariableSources()){
                producerConsumerMap.put(sourceVariableId, variableModel.getId());
            }
        }
    }

    Long findConsumerVariableByProducerVariable(Long producerVariableId) {
        return Optional.ofNullable(producerConsumerMap.get(producerVariableId)).orElseThrow();
    }
}
