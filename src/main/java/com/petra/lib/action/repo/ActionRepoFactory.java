package com.petra.lib.action.repo;

import com.petra.lib.transaction.TransactionManager;

public final class ActionRepoFactory {

    public static ActionRepo createActionRepo(TransactionManager transactionManager){
        return new ActivityRepoImpl(transactionManager);
    }
}
