package com.petra.lib.worker.handler;

import com.petra.lib.worker.variable.group.handler.VariableContext;

import javax.persistence.EntityManager;

public interface UserContext extends VariableContext {
    void putExecutionVariable(String name, Object value);
    EntityManager getEntityManager();
}
