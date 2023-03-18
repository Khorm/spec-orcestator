package com.petra.lib.block;

import com.petra.lib.manager.ExecutionContext;

public interface ExecutingBlock  {
//    ExecutionResponse execute(ExecutionRequest request);
//
//    void rollback(RollbackRequest rollbackRequest);
//
//    List<Long> getTransactionsId();
//
//    Long getBlockId();
//
//    Version getVersion();

    void execute(ExecutionContext executionContext);
}
