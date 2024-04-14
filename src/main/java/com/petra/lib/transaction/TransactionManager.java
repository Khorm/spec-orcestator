package com.petra.lib.transaction;


import com.petra.lib.exception.RepeatedExecutionException;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.annotation.Isolation;

public interface TransactionManager {
    <T> T executeInTransaction(TransactionCallable<T> task, Isolation transactionDefinition) throws Exception;
    <T> T executeInTransaction(TransactionCallable<T> task) throws Exception;

    void executeInTransaction(TransactionRunnable task) throws Exception;

    JpaTransactionManager getJpaTransactionManager();
}
