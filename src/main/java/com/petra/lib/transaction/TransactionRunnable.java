package com.petra.lib.transaction;

import org.springframework.orm.jpa.JpaTransactionManager;

public interface TransactionRunnable {
    void run(JpaTransactionManager jpaTransactionManager);
}
