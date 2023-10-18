package com.petra.lib.transaction;

public interface TransactionManager {
    void executeInTransaction(TransactionRunnable task);
}
