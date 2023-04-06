package com.petra.lib.variable.process;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.petra.lib.variable.base.VariableList;
import com.petra.lib.variable.mapper.VariableMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Объект хранить значения переменных во время процесса
 */
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class ProcessVariablesController {

    VariableList variableList;
    VariableMapper inputValueMap;
    Map<Long, ProcessVariable> processVariableMap = new HashMap<>();
    Map<String, ProcessVariable> processVariableMapByName = new HashMap<>();


    public <T> T getValueByName(String name, Class<T> clazz) throws JsonProcessingException {
        return (T) processVariableMapByName.get(name).getValue(clazz);
    }

    public Collection<ProcessVariable> getAllVariables() {
        return processVariableMap.values();
    }

    public void insertOuterVariables(Collection<ProcessVariable> outerVariables) {
        Collection<ProcessVariable> localVariables = inputValueMap.map(outerVariables);
        localVariables.forEach(this::putProcessVariable);
    }

    public void setVariables(Collection<ProcessVariable> variables){
        variables.forEach(this::putProcessVariable);
    }

    public String getVariablesJson() throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(getAllVariables());
    }

//    public void fromJson(String json) throws JsonProcessingException {
//        List<ProcessVariable> newProcessVariables = new ObjectMapper().readValue(json, new TypeReference<List<ProcessVariable>>() {
//        });
//        newProcessVariables.forEach(this::putProcessVariable);
//    }


    private void putProcessVariable(ProcessVariable processVariable) {
        processVariableMap.put(processVariable.getId(), processVariable);
        processVariableMapByName.put(variableList.getVariableById(processVariable.getId()).getName(), processVariable);
    }
}
