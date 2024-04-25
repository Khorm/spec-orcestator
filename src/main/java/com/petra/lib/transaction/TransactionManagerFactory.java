package com.petra.lib.transaction;

import org.springframework.orm.jpa.JpaTransactionManager;

public final class TransactionManagerFactory {
    public static TransactionManager createTransactionManager(JpaTransactionManager jpaTransactionManager){
        return new TransactionManagerImpl(jpaTransactionManager);
    }
}
