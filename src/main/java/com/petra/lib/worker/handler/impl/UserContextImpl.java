package com.petra.lib.worker.handler.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.petra.lib.PetraException;
import com.petra.lib.manager.block.JobContext;
import com.petra.lib.manager.block.ProcessVariableDto;
import com.petra.lib.variable.base.Variable;
import com.petra.lib.variable.base.VariableList;
import com.petra.lib.worker.handler.UserContext;
import com.petra.lib.worker.variable.group.handler.VariableContext;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.persistence.EntityManager;
import java.util.HashMap;
import java.util.Map;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class UserContextImpl implements UserContext, VariableContext {

    JobContext context;
    EntityManager entityManager;
    VariableList variableList;
    ObjectMapper objectMapper = new ObjectMapper();
    Map<String, Object> valuesCache = new HashMap<>();

    @Override
    public <T> T getExecutionVariable(String variableName, Class<T> clazz){
        if (valuesCache.containsKey(variableName)){
            return (T) valuesCache.get(variableName);
        }
        ProcessVariableDto processVariableDto = context.getVariableById(variableList.getVariableByName(variableName).getId());
        T var;
        try {
            var = objectMapper.readValue(processVariableDto.getValue(), clazz);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new PetraException("Value parse error", e);
        }
        return var;
    }

    @Override
    public void putExecutionVariable(String name, Object value) {
        valuesCache.put(name, value);
    }

    @Override
    public EntityManager getEntityManager() {
        return entityManager;
    }

    public JobContext fillContext(){
        valuesCache.forEach((key, value) -> {
            Variable variable = variableList.getVariableByName(key);
            try {
                ProcessVariableDto processVariableDto =
                        new ProcessVariableDto(variable.getId(), objectMapper.writeValueAsString(value));
                context.setVariable(processVariableDto);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                throw new PetraException("Parsing error", e);
            }
        });
        return context;
    }


}
