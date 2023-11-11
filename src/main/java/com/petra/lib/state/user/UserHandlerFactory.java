package com.petra.lib.state.user;

import com.petra.lib.state.StateHandler;
import com.petra.lib.state.user.handler.UserActionHandler;
import com.petra.lib.state.user.handler.UserSourceHandler;
import com.petra.lib.transaction.TransactionManager;

public class UserHandlerFactory {

    public static StateHandler createActionUserHandler(TransactionManager transactionManager, UserActionHandler userActionHandler){
        return new UserExecutor(transactionManager, userActionHandler, null, false);
    }

    public static StateHandler createSourceStateHandler(TransactionManager transactionManager, UserSourceHandler sourceHandler){
        return new UserExecutor(transactionManager, null,sourceHandler, false);
    }
}
