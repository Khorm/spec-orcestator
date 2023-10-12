package com.petra.lib.context;

import com.petra.lib.state.variable.group.handler.VariableContext;

import javax.persistence.EntityManager;

public interface UserContext extends VariableContext {
    void putExecutionVariable(String name, Object value);
    EntityManager getEntityManager();
}
