package com.petra.lib.transaction;

import com.petra.lib.exception.RepeatedExecutionException;
import org.springframework.orm.jpa.JpaTransactionManager;

public interface TransactionCallable<T> {
    T run(Transaction transaction) throws Exception;
}
