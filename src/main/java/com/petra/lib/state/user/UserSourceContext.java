package com.petra.lib.state.user;

import javax.persistence.EntityManager;

public interface UserSourceContext {
    <T> T getValue(String variableName, Class<T> clazz);
    EntityManager getEntityManager();
}
