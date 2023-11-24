package com.petra.lib.context.value;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Часть общего контекста, хранящая и синхронизирующая изменения с базой
 */
public class SimpleVariablesContainer implements VariablesContainer {

  final Map<Long, ProcessValue> processValueMap = new HashMap<>();

  @Override
  public synchronized void addVariable(ProcessValue processValue) {
    processValueMap.put(processValue.getVariableId(), processValue);
  }

  @Override
  public void addVariables(VariablesContainer inputContext) {
    Map<Long, ProcessValue> inputVars = inputContext.getValues().stream()
        .collect(Collectors.toMap(ProcessValue::getVariableId, Function.identity()));
    this.processValueMap.putAll(inputVars);
  }

  @Override
  public ProcessValue getValueByName(String name) {
    for (Map.Entry<Long, ProcessValue> entry : processValueMap.entrySet()) {
      if (entry.getValue().getName().equals(name)) {
        return entry.getValue();
      }
    }
    throw new NullPointerException("No value found for name " + name);
  }

  @Override
  public Optional<ProcessValue> getValueById(Long id) {
    if (processValueMap.containsKey(id)) {
      return Optional.of(processValueMap.get(id));
    }
    return Optional.empty();
  }

  @Override
  public Collection<ProcessValue> getValues() {
    return processValueMap.values();
  }
}
