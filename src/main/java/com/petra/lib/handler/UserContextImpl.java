package com.petra.lib.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.petra.lib.manager.block.ExecutionContext;

class UserContextImpl implements UserContext{

    private final ExecutionContext context;

    UserContextImpl(ExecutionContext context){
        this.context = context;
    }

    @Override
    public <T> T getExecutionVariable(String variableName, Class<T> clazz) throws JsonProcessingException {
        return context.getVariableByName(variableName, clazz);
    }
}
