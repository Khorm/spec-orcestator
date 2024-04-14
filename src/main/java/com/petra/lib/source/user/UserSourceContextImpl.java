package com.petra.lib.source.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.petra.lib.variable.pure.PureVariable;
import com.petra.lib.variable.pure.PureVariableList;
import com.petra.lib.variable.value.ProcessValue;
import com.petra.lib.variable.value.VariableContainerFactory;
import com.petra.lib.variable.value.VariablesContainer;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.persistence.EntityManager;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
class UserSourceContextImpl implements UserSourceContext {
    EntityManager entityManager;

    @Getter
    VariablesContainer resultContainer = VariableContainerFactory.getSimpleVariableContainer();
    VariablesContainer argumentsContainer;
    PureVariableList pureVariableList;

    @Override
    public <T> T getValue(String variableName, Class<T> clazz) {
        try {
            return argumentsContainer.getValueByName(variableName).getParsedVariable(clazz);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void setValue(String variableName, Object value) {
        PureVariable pureVariable = pureVariableList.getVariableByName(variableName);
        try {
            resultContainer.addVariable(new ProcessValue(pureVariable.getId(), pureVariable.getName(), value));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public EntityManager getEntityManager() {
        return entityManager;
    }
}
