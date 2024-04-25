package com.petra.lib.transaction;


import com.petra.lib.exception.RepeatedExecutionException;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.annotation.Isolation;

public interface TransactionManager {
    <T> T executeInTransaction(TransactionCallable<T> task, Isolation transactionDefinition);
    <T> T executeInTransaction(TransactionCallable<T> task);

    void executeInTransaction(TransactionRunnable task);

    JpaTransactionManager getJpaTransactionManager();
}
