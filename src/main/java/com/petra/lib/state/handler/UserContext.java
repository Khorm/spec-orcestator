package com.petra.lib.state.handler;

import javax.persistence.EntityManager;

public interface UserContext {

    <T> T getValue(String variableName, Class<T> clazz);
    void setValue(String variableName, Object value);

    EntityManager getEntityManager();

}
