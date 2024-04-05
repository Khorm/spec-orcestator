package com.petra.lib.action.new_action.repo;

import com.petra.lib.action.new_action.ActionContext;
import com.petra.lib.action.new_action.ActionState;
import com.petra.lib.exception.RepeatedExecutionException;
import com.petra.lib.variable.value.VariablesContainer;

import java.util.UUID;

public interface ActionRepo {

    void updateActionStatus(UUID scenario, Long actionId, ActionState actionState) throws RepeatedExecutionException;

    void updateActionContextVariables(UUID scenario, Long blockId, VariablesContainer variablesContainer);

    /**
     * @param actionContext
     * @return true - если удалось сохранить контекст
     * false - если не удалось сохранить контекст так как он уже существует
     */
    boolean saveContext(ActionContext actionContext);

}
