package com.petra.lib.worker.variable.group.handler;

public interface VariableContext {
    <T> T getExecutionVariable(String variableName, Class<T> clazz);
}
