package com.petra.lib.state.variable.neww.loaders.user;

import com.fasterxml.jackson.core.JsonProcessingException;

import javax.persistence.EntityManager;

public interface UserContext {
    <T> T getExecutionVariable(String variableName, Class<T> clazz) throws JsonProcessingException;
    void putExecutionVariable(String name, Object value) throws JsonProcessingException;
    EntityManager getEntityManager();
}
