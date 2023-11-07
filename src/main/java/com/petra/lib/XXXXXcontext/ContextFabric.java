package com.petra.lib.XXXXXcontext;

import com.petra.lib.state.variable.loaders.handler.VariableUserContext;
import com.petra.lib.block.VersionId;
import com.petra.lib.remote.signal.Signal;
import com.petra.lib.variable.pure.PureVariableList;
import lombok.Setter;

import javax.persistence.EntityManager;

public class ContextFabric {

    @Setter
    private static EntityManager entityManager;
    public static DirtyContext createContext(Signal signal, PureVariableList pureVariableList, VersionId blockId){
         return new DutryContextImpl(signal, pureVariableList, blockId);
    }

    public static VariableUserContext createUserContext(DirtyContext dirtyContext){
        new ImplUserContext(dirtyContext, entityManager, statelessVariableList);
    }



//    public static ExecutionContext createWorkflowContext(){
//
//    }
}
