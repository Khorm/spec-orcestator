package com.petra.lib.transaction;

import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

public class TransactionManagerImpl implements TransactionManager {
    private final JpaTransactionManager jpaTransactionManager;

    public TransactionManagerImpl(JpaTransactionManager jpaTransactionManager) {
        this.jpaTransactionManager = jpaTransactionManager;
    }

    public <T> T executeInTransaction(TransactionCallable<T> task, int transactionDefinition) {
        DefaultTransactionDefinition definition = new DefaultTransactionDefinition();
        definition.setIsolationLevel(transactionDefinition);
        definition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus transactionStatus = jpaTransactionManager.getTransaction(definition);
        try {
            return task.run(jpaTransactionManager);
        }catch (Exception e){
            jpaTransactionManager.rollback(transactionStatus);
            throw e;
        }finally {
            jpaTransactionManager.commit(transactionStatus);
        }
    }

    @Override
    public <T> T executeInTransaction(TransactionCallable<T> task) {
        return executeInTransaction(task, TransactionDefinition.ISOLATION_READ_COMMITTED);
    }

    @Override
    public void executeInTransaction(TransactionRunnable task) {
        DefaultTransactionDefinition definition = new DefaultTransactionDefinition();
        definition.setIsolationLevel(TransactionDefinition.ISOLATION_READ_COMMITTED);
        definition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        definition.setReadOnly(true);
        TransactionStatus transactionStatus = jpaTransactionManager.getTransaction(definition);
        try {
            task.run(jpaTransactionManager);
        }catch (Exception e){
            jpaTransactionManager.rollback(transactionStatus);
            throw e;
        }finally {
            jpaTransactionManager.commit(transactionStatus);
        }
    }

    public  JpaTransactionManager getJpaTransactionManager(){
        return jpaTransactionManager;
    }
}
