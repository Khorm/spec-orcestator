package com.petra.lib.worker.handler;

import javax.persistence.EntityManager;

public interface UserContext {
    <T> T getExecutionVariable(String variableName, Class<T> clazz);
    void putExecutionVariable(String name, Object value);
    EntityManager getEntityManager();
}
