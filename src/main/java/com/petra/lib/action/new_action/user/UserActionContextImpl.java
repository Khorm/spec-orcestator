package com.petra.lib.action.new_action.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.petra.lib.action.new_action.ActionContext;
import com.petra.lib.source.handler.user.UserSourceContext;
import com.petra.lib.variable.value.ProcessValue;
import com.petra.lib.variable.pure.PureVariable;
import com.petra.lib.variable.pure.PureVariableList;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.persistence.EntityManager;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
class UserActionContextImpl implements UserActionContext, UserSourceContext {

    EntityManager entityManager;
    ActionContext actionContext;
    PureVariableList pureVariablesList;

    @Override
    public <T> T getValue(String variableName, Class<T> clazz) {
        try {
            return actionContext.getValueByVariableName(variableName).getParsedVariable(clazz);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void setValue(String variableName, Object value) {
        PureVariable pureVariable = pureVariablesList.getVariableByName(variableName);

        try {
            actionContext.setValue(new ProcessValue(pureVariable.getId(), pureVariable.getName(), value));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public EntityManager getEntityManager() {
        return entityManager;
    }



}
