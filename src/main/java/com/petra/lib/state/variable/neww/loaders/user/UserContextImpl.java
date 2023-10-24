package com.petra.lib.state.variable.neww.loaders.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.petra.lib.environment.context.ActivityContext;
import com.petra.lib.environment.context.ProcessValue;
import com.petra.lib.variable.pure.PureVariableList;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.persistence.EntityManager;
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class UserContextImpl implements UserContext{

    ActivityContext activityContext;
    EntityManager entityManager;
    ObjectMapper om = new ObjectMapper();
    PureVariableList pureVariableList;


    @Override
    public <T> T getExecutionVariable(String variableName, Class<T> clazz) throws JsonProcessingException {
        return om.readValue(activityContext.getVariablesContext().getProcessValueByName(variableName).getJsonValue(), clazz) ;
    }

    @Override
    public void putExecutionVariable(String name, Object value) throws JsonProcessingException {
        Long idProcessValue = pureVariableList.getVariableByName(name).getId();
        ProcessValue processValue = new ProcessValue(idProcessValue, name, true, om.writeValueAsString(value));
        activityContext.getVariablesContext().setVariable(processValue);
    }

    @Override
    public EntityManager getEntityManager() {
        return entityManager;
    }
}
