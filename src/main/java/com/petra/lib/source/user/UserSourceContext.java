package com.petra.lib.source.user;

import javax.persistence.EntityManager;

public interface UserSourceContext {
    <T> T getValue(String variableName, Class<T> clazz);

    void setValue(String variableName, Object value);
    EntityManager getEntityManager();
}
