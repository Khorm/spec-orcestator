package com.petra.lib.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.petra.lib.manager.block.JobContext;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.persistence.EntityManager;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
class UserContextImpl implements UserContext{

    JobContext context;
    EntityManager entityManager;

    @Override
    public <T> T getExecutionVariable(String variableName, Class<T> clazz) throws JsonProcessingException {
        return context.getVariableByName(variableName, clazz);
    }

    @Override
    public EntityManager getEntityManager() {
        return null;
    }
}
