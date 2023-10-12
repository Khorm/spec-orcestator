package com.petra.lib.state.variable.group.handler;

public interface VariableContext {
    <T> T getExecutionVariable(String variableName, Class<T> clazz);
}
