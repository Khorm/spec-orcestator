package com.petra.lib.transaction;

import com.petra.lib.exception.RepeatedExecutionException;
import org.springframework.orm.jpa.JpaTransactionManager;

public interface TransactionRunnable {
    void run(JpaTransactionManager jpaTransactionManager) throws RepeatedExecutionException;
}
