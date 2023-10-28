package com.petra.lib.state.variable.neww.loaders.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.petra.lib.context.ActivityContext;
import com.petra.lib.state.variable.neww.ParsedValue;
import com.petra.lib.state.variable.neww.ProcessValue;
import com.petra.lib.variable.pure.PureVariable;
import com.petra.lib.variable.pure.PureVariableList;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.persistence.EntityManager;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class ImplUserContext implements UserContext {

    EntityManager entityManager;
    ActivityContext activityContext;
    PureVariableList pureVariableList;
    ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void putExecutionVariable(String name, Object value) throws JsonProcessingException {
        PureVariable pureVariable = pureVariableList.getVariableByName(name);

        ProcessValue processValue = new ParsedValue(pureVariable.getId(), name, true,
                objectMapper.writeValueAsString(value), value);
        activityContext.getVariablesContext().setVariable(processValue);
    }

    @Override
    public EntityManager getEntityManager() {
        return entityManager;
    }

    @Override
    public <T> T getExecutionVariable(String variableName, Class<T> clazz) throws JsonProcessingException {
        return (T) activityContext.getVariablesContext().getProcessValueByName(variableName).getParsedVariable(clazz);
    }
}
