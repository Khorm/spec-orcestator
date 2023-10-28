package com.petra.lib.transaction;

import org.springframework.orm.jpa.JpaTransactionManager;

public interface TransactionRunnable<T> {
    T run(JpaTransactionManager jpaTransactionManager);
}
