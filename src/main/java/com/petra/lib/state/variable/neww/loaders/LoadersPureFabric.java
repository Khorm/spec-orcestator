package com.petra.lib.state.variable.neww.loaders;

import org.springframework.orm.jpa.JpaTransactionManager;

import java.util.Collection;

public final class LoadersPureFabric {

    public static VariableLoader getHandlerLoader(JpaTransactionManager jpaTransactionManager,
                                                  Collection<UserVariableHandler> userHandlers){


        return new HandlerLoader()
    }


    public static VariableLoader getSourceLoader(){
        return
    }
}
