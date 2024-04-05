package com.petra.lib.action.new_action.user;

import javax.persistence.EntityManager;

public interface UserActionContext {

    <T> T getValue(String variableName, Class<T> clazz);
    void setValue(String variableName, Object value);

    EntityManager getEntityManager();

}
