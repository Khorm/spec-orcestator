package com.petra.lib.XXXXXcontext;

import com.petra.lib.state.variable.neww.loaders.user.UserContext;
import com.petra.lib.state.variable.neww.loaders.user.ImplUserContext;
import com.petra.lib.block.BlockId;
import com.petra.lib.environment.dto.Signal;
import com.petra.lib.variable.pure.PureVariableList;
import lombok.Setter;

import javax.persistence.EntityManager;

public class ContextFabric {

    @Setter
    private static EntityManager entityManager;
    public static DirtyContext createContext(Signal signal, PureVariableList pureVariableList, BlockId blockId){
         return new DutryContextImpl(signal, pureVariableList, blockId);
    }

    public static UserContext createUserContext(DirtyContext dirtyContext){
        new ImplUserContext(dirtyContext, entityManager, statelessVariableList);
    }



//    public static ExecutionContext createWorkflowContext(){
//
//    }
}
