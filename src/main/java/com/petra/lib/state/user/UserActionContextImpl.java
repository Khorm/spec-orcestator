package com.petra.lib.state.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.petra.lib.context.ActivityContext;
import com.petra.lib.context.value.ProcessValue;
import com.petra.lib.variable.pure.PureVariable;
import com.petra.lib.variable.pure.PureVariableList;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.persistence.EntityManager;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
class UserActionContextImpl implements UserActionContext, UserSourceContext{

    EntityManager entityManager;
    ActivityContext activityContext;

    @Override
    public <T> T getValue(String variableName, Class<T> clazz) {
        try {
            return activityContext.getValueByVariableName(variableName).getParsedVariable(clazz);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void setValue(String variableName, Object value) {
        PureVariableList pureVariableList = activityContext.getActivityVariables();
        PureVariable pureVariable = pureVariableList.getVariableByName(variableName);

        try {
            activityContext.setValue(new ProcessValue(pureVariable.getId(), pureVariable.getName(), value));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public EntityManager getEntityManager() {
        return entityManager;
    }
}
