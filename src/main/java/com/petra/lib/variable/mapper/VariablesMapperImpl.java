package com.petra.lib.variable.mapper;

import com.petra.lib.context.value.ProcessValue;
import com.petra.lib.context.value.SimpleVariablesContainer;
import com.petra.lib.context.value.VariablesContainer;
import lombok.RequiredArgsConstructor;

/**
 * Класс непосредственно переделывающий входящую коллекцию переменных в исходящую
 */

@RequiredArgsConstructor
class VariablesMapperImpl implements VariableMapper {

  /**
   * Коллекция переменных владельца
   */
  private final VariableMapCollection consumerVariableCollection;

  @Override
  public VariablesContainer map(VariablesContainer producerCollection) {
    VariablesContainer consumerVariables = new SimpleVariablesContainer();
    for (ProcessValue producerVariable : producerCollection.getValues()) {
      ProcessValue consumerProcessValue = map(producerVariable);
      if (consumerProcessValue == null) {
        continue;
      }
      consumerVariables.addVariable(consumerProcessValue);
    }
    return consumerVariables;
  }

  @Override
  public ProcessValue map(ProcessValue producerVariable) {
    MapperVariableModel consumerVariable = consumerVariableCollection.findConsumerVariableByProducerVariable(
        producerVariable.getVariableId());
    if (consumerVariable == null) {
      return null;
    }
    return new ProcessValue(consumerVariable.getVariableId(), consumerVariable.getName(),
        producerVariable.getJsonValue());
  }
}
