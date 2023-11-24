package com.petra.lib.variable.mapper;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Коллекция хранит значения маппинг переменных. Возвращает переменную консумент по айди переменной
 * продюсера
 */
class VariableMapCollection {

  private final Map<Long, MapperVariableModel> producerConsumerMap = new HashMap<>();

  VariableMapCollection(Collection<MapperVariableModel> variableModels) {
    for (MapperVariableModel variableModel : variableModels) {
      if (variableModel.getSourceVariables() == null) {
        continue;
      }
      for (Long sourceVariableId : variableModel.getSourceVariables()) {
        producerConsumerMap.put(sourceVariableId, variableModel);
      }
    }
  }

  MapperVariableModel findConsumerVariableByProducerVariable(Long producerVariableId) {
    return producerConsumerMap.get(producerVariableId);
  }
}
