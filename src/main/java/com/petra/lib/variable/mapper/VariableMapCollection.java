package com.petra.lib.variable.mapper;

import lombok.RequiredArgsConstructor;

import java.util.Map;
import java.util.Optional;

/**
 * Коллекция хранит значения маппинга переменных.
 * Возвращает переменную консумера по айди переменной продюсера
 */
@RequiredArgsConstructor
class VariableMapCollection {
    private final Map<Long, Long> producerConsumerMap;

    Long findConsumerVariableByProducerVariable(Long producerVariableId) {
        return Optional.ofNullable(producerConsumerMap.get(producerVariableId)).orElseThrow();
    }
}
