package com.petra.lib.handler;

import com.fasterxml.jackson.core.JsonProcessingException;

import javax.persistence.EntityManager;

public interface UserContext {
    <T> T getExecutionVariable(String variableName, Class<T> clazz) throws JsonProcessingException;
    EntityManager getEntityManager();
}
