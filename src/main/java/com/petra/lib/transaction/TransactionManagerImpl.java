package com.petra.lib.transaction;

import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.support.DefaultTransactionDefinition;

public class TransactionManagerImpl implements TransactionManager {
    private final JpaTransactionManager jpaTransactionManager;

    public TransactionManagerImpl(JpaTransactionManager jpaTransactionManager) {
        this.jpaTransactionManager = jpaTransactionManager;
    }


    public <T> T executeInTransaction(TransactionCallable<T> task, Isolation transactionDefinition) throws Exception {
        DefaultTransactionDefinition definition = new DefaultTransactionDefinition();
        definition.setIsolationLevel(transactionDefinition.value());
        definition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus transactionStatus = jpaTransactionManager.getTransaction(definition);
        try {
            Transaction transaction = new Transaction(transactionStatus, jpaTransactionManager);
            T result = task.run(transaction);
            transactionAccept(transactionStatus, transaction);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            jpaTransactionManager.rollback(transactionStatus);
            throw e;
        }

    }


    @Override
    public <T> T executeInTransaction(TransactionCallable<T> task) throws Exception {
        return executeInTransaction(task, Isolation.READ_COMMITTED);
    }

    @Override
    public void executeInTransaction(TransactionRunnable task) throws Exception {
        DefaultTransactionDefinition definition = new DefaultTransactionDefinition();
        definition.setIsolationLevel(TransactionDefinition.ISOLATION_READ_COMMITTED);
        definition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        definition.setReadOnly(true);
        TransactionStatus transactionStatus = jpaTransactionManager.getTransaction(definition);
        Transaction transaction = new Transaction(transactionStatus, jpaTransactionManager);
        try {
            task.run(transaction);
            transactionAccept(transactionStatus, transaction);
        } catch (Exception e) {
            jpaTransactionManager.rollback(transactionStatus);
            e.printStackTrace();
            throw e;
        }
    }

    public JpaTransactionManager getJpaTransactionManager() {
        return jpaTransactionManager;
    }

    private void transactionAccept(TransactionStatus transactionStatus, Transaction transaction ){
        if (transaction.isTransactionSuccess()){
            jpaTransactionManager.commit(transactionStatus);
        }else {
            jpaTransactionManager.rollback(transactionStatus);
        }
    }
}
