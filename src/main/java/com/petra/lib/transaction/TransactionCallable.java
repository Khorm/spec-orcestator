package com.petra.lib.transaction;

import org.springframework.orm.jpa.JpaTransactionManager;

public interface TransactionCallable<T> {
    T run(JpaTransactionManager jpaTransactionManager);
}
