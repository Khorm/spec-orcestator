package com.petra.lib.variable.value;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Часть общего контекста, хранящяя и синхронизирующяя изменения с базой
 */
class VariablesContainerImpl implements VariablesContainer{
    @Override
    public void addVariable(ProcessValue processValue) {

    }

    @Override
    public void addVariables(VariablesContainer inputContext) {

    }

    @Override
    public ProcessValue getValueByName(String name) {
        return null;
    }

    @Override
    public Optional<ProcessValue> getValueById(Long id) {
        return Optional.empty();
    }

    @Override
    public Collection<ProcessValue> getValues() {
        return null;
    }

    @Override
    public String toJson() {
        return null;
    }
//    final Map<Long, ProcessValue> processValueMap = new HashMap<>();
//
//    @Override
//    public void addVariable(ProcessValue processValue) {
//        processValueMap.put(processValue.getVariableId(), processValue);
//    }
//
//    @Override
//    public void addVariables(VariablesContainer inputContext) {
//        Map<Long, ProcessValue> inputVars = inputContext.getValues().stream()
//                .collect(Collectors.toMap(ProcessValue::getVariableId, Function.identity()));
//        this.processValueMap.putAll(inputVars);
//    }
//
//    @Override
//    public ProcessValue getValueByName(String name) {
//        for (Map.Entry<Long, ProcessValue> entry : processValueMap.entrySet()){
//            if (entry.getValue().getName().equals(name)){
//                return entry.getValue();
//            }
//        }
//        throw new NullPointerException("No value found for name " + name);
//    }
//
//    @Override
//    public Optional<ProcessValue> getValueById(Long id) {
//        if (processValueMap.containsKey(id)){
//            return Optional.of(processValueMap.get(id));
//        }
//        return Optional.empty();
//    }
//
//    @Override
//    public Collection<ProcessValue> getValues() {
//        return processValueMap.values();
//    }
}
