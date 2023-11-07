package com.petra.lib.state.variable.loaders.handler;

public interface VariableUserContext {
    <T> T getValue(String variableName, Class<T> clazz);
}
