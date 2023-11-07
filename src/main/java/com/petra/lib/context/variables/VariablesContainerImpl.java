package com.petra.lib.context.variables;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

/**
 * „асть общего контекста, хран€щ€€ и синхронизирующ€€ изменени€ с базой
 */
@FieldDefaults(level = AccessLevel.PRIVATE)
public class VariablesContainerImpl implements VariablesContainer {

    /**
     * Ќепосредественно хран€ща€ переменные мапа.
     * ¬сегда в согласованном состо€нии. ’ранит только заполненные переменные.
     */
    final Map<Long, ProcessValue> processValueMap = new HashMap<>();

    final VariablesSynchRepo variablesSynchRepo;

    final UUID actionId;

    public VariablesContainerImpl(VariablesSynchRepo variablesSynchRepo, UUID actionId) {
        this.variablesSynchRepo = variablesSynchRepo;
        this.actionId = actionId;
    }


    public synchronized void addVariable(ProcessValue processValue) {
        processValueMap.put(processValue.getVariableId(), processValue);
        variablesSynchRepo.commitValues(processValueMap);
    }



    public synchronized void addVariables(VariablesContainerImpl inputContext) {
        this.processValueMap.putAll(inputContext.processValueMap);
        variablesSynchRepo.commitValues(processValueMap);
    }

    public synchronized ProcessValue getValueByName(String name) {
        for (Map.Entry<Long, ProcessValue> entry : processValueMap.entrySet()){
            if (entry.getValue().getName().equals(name)){
                return entry.getValue();
            }
        }
        throw new NullPointerException("No value found for name " + name);
    }

    public synchronized Optional<ProcessValue> getValueById(Long id){
        if (processValueMap.containsKey(id)){
            return Optional.of(processValueMap.get(id));
        }
        return Optional.empty();
    }
}
