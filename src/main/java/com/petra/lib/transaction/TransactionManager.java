package com.petra.lib.transaction;


import org.springframework.orm.jpa.JpaTransactionManager;

public interface TransactionManager {
    <T> T commitInTransaction(TransactionCallable<T> task, int transactionDefinition);
    <T> T commitInTransaction(TransactionCallable<T> task);

    void commitInTransaction(TransactionRunnable task);


    JpaTransactionManager getJpaTransactionManager();
}
