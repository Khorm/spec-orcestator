package com.petra.lib.transaction;

import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

class TransactionManagerImpl implements TransactionManager {
    private static JpaTransactionManager jpaTransactionManager;

    @Override
    public void executeInTransaction(TransactionRunnable task) {
        DefaultTransactionDefinition definition = new DefaultTransactionDefinition();
        TransactionStatus transactionStatus = jpaTransactionManager.getTransaction(definition);
        try {
            task.run(jpaTransactionManager);
            jpaTransactionManager.commit(transactionStatus);
        }catch (Exception e){
            jpaTransactionManager.rollback(transactionStatus);
            throw e;
        }
    }
}
