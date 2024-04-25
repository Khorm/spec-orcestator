package com.petra.lib.variable.value;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Часть общего контекста, хранящяя и синхронизирующяя изменения с базой
 */
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
class ValuesContainerImpl implements ValuesContainer {

    Map<String, ProcessValue> valueByNameMap;
    Map<Long, ProcessValue> valueByIdMap;
    Collection<ProcessValue> valueCollection;

    ValuesContainerImpl(Collection<ProcessValue> valueCollection){
        this.valueCollection = valueCollection;

        this.valueByIdMap = valueCollection.stream()
                .collect(Collectors.toMap(ProcessValue::getVariableId, Function.identity()));
        this.valueByNameMap = valueCollection.stream()
                .collect(Collectors.toMap(ProcessValue::getName, Function.identity()));
    }

    ValuesContainerImpl(){
        valueByIdMap = new HashMap<>();
        valueByNameMap = new HashMap<>();
        valueCollection = new ArrayList<>();
    }


    @Override
    public void addValue(ProcessValue processValue) {
        valueByNameMap.put(processValue.getName(), processValue);
        valueByIdMap.put(processValue.getVariableId(), processValue);
        valueCollection.add(processValue);
    }

    @Override
    public void addValues(ValuesContainer inputContext) {
        addValues(inputContext.getValues());
    }

    @Override
    public void addValues(Collection<ProcessValue> inputContext) {
        for(ProcessValue processValue : inputContext){
            addValue(processValue);
        }
    }

    @Override
    public ProcessValue getValueByName(String name) {
        return valueByNameMap.get(name);
    }

    @Override
    public ProcessValue getValueById(Long id) {
        return valueByIdMap.get(id);
    }

    @Override
    public Collection<ProcessValue> getValues() {
        return Collections.unmodifiableCollection(valueCollection);
    }

}
