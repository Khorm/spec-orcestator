package com.petra.lib.transaction;


import org.springframework.orm.jpa.JpaTransactionManager;

public interface TransactionManager {
    <T> T executeInTransaction(TransactionCallable<T> task, int transactionDefinition);
    <T> T executeInTransaction(TransactionCallable<T> task);

    void executeInTransaction(TransactionRunnable task);

    JpaTransactionManager getJpaTransactionManager();
}
