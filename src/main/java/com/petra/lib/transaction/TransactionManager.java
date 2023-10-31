package com.petra.lib.transaction;


import org.springframework.orm.jpa.JpaTransactionManager;

public interface TransactionManager {
    <T> T executeInTransaction(TransactionRunnable<T> task, int transactionDefinition);
    <T> T executeInTransaction(TransactionRunnable<T> task);
    JpaTransactionManager getJpaTransactionManager();
}
