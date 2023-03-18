package com.petra.lib.action.execution;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface ActionContext {

    <T> T getExecutionVariable(String variableName, Class<T> clazz) throws JsonProcessingException;

    void setCompensationVariable(String name, Object variable);
}
