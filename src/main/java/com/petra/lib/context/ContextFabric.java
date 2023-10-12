package com.petra.lib.context;

import com.petra.lib.block.BlockId;
import com.petra.lib.signal.dto.RequestDto;
import com.petra.lib.variable.base.StatelessVariableList;
import lombok.Setter;

import javax.persistence.EntityManager;

public class ContextFabric {

    @Setter
    private static EntityManager entityManager;
    public static ExecutionContext createContext(RequestDto request, StatelessVariableList statelessVariableList, BlockId blockId){
         return new ActionContext(request, statelessVariableList, blockId);
    }

    public static UserContext createUserContext(ExecutionContext executionContext){
        new UserContextImpl(executionContext, entityManager, statelessVariableList);
    }



//    public static ExecutionContext createWorkflowContext(){
//
//    }
}
