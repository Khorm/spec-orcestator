package com.petra.lib.state.variable.loader.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.petra.lib.context.ActivityContext;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

/**
 * Пользовательский контекст для выгрузки значения
 */
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class VariableUserContextImpl implements VariableUserContext {

    private final ActivityContext activityContext;

    @Override
    public <T> T getValue(String variableName, Class<T> clazz) {
        try {
            return activityContext.getValueByVariableName(variableName).getParsedVariable(clazz);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
