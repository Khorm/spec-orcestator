package com.petra.lib.variable.process;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.petra.lib.variable.base.VariableList;
import com.petra.lib.variable.mapper.VariableMapCollection;
import com.petra.lib.variable.process.ProcessVariable;
import com.petra.lib.workflow.model.ValueList;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

/**
 * Объект хранить значения переменных во время процесса
 */
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProcessVariablesCollection {

    VariableList variableList;
    Map<Long, ProcessVariable> processVariableMap = new HashMap<>();
    Map<String, ProcessVariable> processVariableMapByName = new HashMap<>();

    public ProcessVariablesCollection(VariableList variableList){
        this.variableList = variableList;
    }


    public <T> T getValueByName(String name, Class<T> clazz) throws JsonProcessingException {
        return (T) processVariableMapByName.get(name).getValue(clazz);
    }

    public Collection<ProcessVariable> getAllVariables(){
        return processVariableMap.values();
    }

    public void putProcessVariable(ProcessVariable processVariable){
        processVariableMap.put(processVariable.getId(), processVariable);
        processVariableMapByName.put(variableList.getVariableById(processVariable.getId()).getName(), processVariable);
    }

    public String getJson() throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(getAllVariables());
    }

    public void fromJson(String json) throws JsonProcessingException {
        List<ProcessVariable> newProcessVariables = new ObjectMapper().readValue(json, new TypeReference<List<ProcessVariable>>() {
        });
        newProcessVariables.forEach(this::putProcessVariable);
    }
}
