package com.petra.lib.action.repo;

import com.petra.lib.action.ActionContext;
import com.petra.lib.action.BlockState;
import com.petra.lib.variable.value.VariablesContainer;

import java.util.UUID;

public interface ActionRepo {

    boolean updateActionContextVariables(UUID scenario, Long actionId,
                                         VariablesContainer actionVariables, BlockState actionState) throws Exception;

    boolean updateActionState(UUID scenario, Long actionId, BlockState actionState) throws Exception;



    /**
     * @param actionContext
     * @return true - если удалось сохранить контекст
     * false - если не удалось сохранить контекст так как он уже существует
     */
    boolean createContext(ActionContext actionContext);

}
