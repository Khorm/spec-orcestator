package com.petra.lib.state.variable.loader.handler;

public interface VariableUserContext {
    <T> T getValue(String variableName, Class<T> clazz);
}
