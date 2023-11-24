package com.petra.lib.context.value;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

/**
 * „асть общего контекста, хран€ща€ и синхронизирующа€ изменени€ с базой
 */
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DBVariablesContextImpl implements VariablesContainer {

  /**
   * Ќепосредственно хран€ща€ переменные мапа по ID. ¬сегда в согласованном состо€нии. ’ранит только
   * заполненные переменные.
   */
  final Map<Long, ProcessValue> processValueMap = new HashMap<>();

  final VariablesSynchRepo variablesSynchRepo;


  public DBVariablesContextImpl(VariablesSynchRepo variablesSynchRepo) {
    this.variablesSynchRepo = variablesSynchRepo;
  }


  public synchronized void addVariable(ProcessValue processValue, UUID actionId) {
    processValueMap.put(processValue.getVariableId(), processValue);
    variablesSynchRepo.commitValues(processValueMap, actionId);
  }


  public synchronized void addVariables(VariablesContainer inputContext, UUID actionId) {
    Map<Long, ProcessValue> inputVars = inputContext.getValues().stream()
        .collect(Collectors.toMap(ProcessValue::getVariableId, Function.identity()));
    this.processValueMap.putAll(inputVars);
    variablesSynchRepo.commitValues(processValueMap, actionId);
  }

  @Override
  public void addVariable(ProcessValue processValue) {

  }

  @Override
  public void addVariables(VariablesContainer inputContext) {

  }

  public synchronized ProcessValue getValueByName(String name) {
    for (Map.Entry<Long, ProcessValue> entry : processValueMap.entrySet()) {
      if (entry.getValue().getName().equals(name)) {
        return entry.getValue();
      }
    }
    throw new NullPointerException("No value found for name " + name);
  }

  public synchronized Optional<ProcessValue> getValueById(Long id) {
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
