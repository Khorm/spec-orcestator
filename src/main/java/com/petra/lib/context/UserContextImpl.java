package com.petra.lib.context;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.petra.lib.PetraException;
import com.petra.lib.block.ProcessValue;
import com.petra.lib.variable.base.StatelessVariable;
import com.petra.lib.variable.base.StatelessVariableList;
import com.petra.lib.state.variable.group.handler.VariableContext;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.persistence.EntityManager;
import java.util.HashMap;
import java.util.Map;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class UserContextImpl implements UserContext, VariableContext {

    ActionContext context;
    EntityManager entityManager;
    StatelessVariableList statelessVariableList;
    ObjectMapper objectMapper = new ObjectMapper();
    Map<String, Object> valuesCache = new HashMap<>();

    @Override
    public <T> T getExecutionVariable(String variableName, Class<T> clazz){
        if (valuesCache.containsKey(variableName)){
            return (T) valuesCache.get(variableName);
        }
        ProcessValue processValue = context.getVariableById(statelessVariableList.getVariableByName(variableName).getId());
        T var;
        try {
            var = objectMapper.readValue(processValue.getValue(), clazz);
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

    public ActionContext fillContext(){
        valuesCache.forEach((key, value) -> {
            StatelessVariable statelessVariable = statelessVariableList.getVariableByName(key);
            try {
                ProcessValue processValue =
                        new ProcessValue(statelessVariable.getId(), objectMapper.writeValueAsString(value));
                context.setVariable(processValue);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                throw new PetraException("Parsing error", e);
            }
        });
        return context;
    }


}
