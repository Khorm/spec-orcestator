package com.petra.lib.transaction;

/**
 * Менеджер транзакций. Хранит и перпзаписывает точки отката
 */
public class TransactionImpl {

////    @Getter
////    private final List<ExecutingBlock> transactionExecutingBlocks = new ArrayList<>();
//    @Getter
//    private Optional<Long> currentTransactionId;
////    private ExecutingBlock executingExecutingBlock;
////    private Iterator<ExecutingBlock> compensateIterator;
//
//    TransactionImpl(Long currentTransactionId){
//        this.currentTransactionId = Optional.ofNullable(currentTransactionId);
//    }
//
//
////    public void setCurrentExecutor(ExecutingBlock executingBlock){
////        if (currentTransactionId.isEmpty()) return;
////        if (executingExecutingBlock != null) {
////            transactionExecutingBlocks.add(executingExecutingBlock);
////        }
////        executingExecutingBlock = executingBlock;
////    }
//
//    public void setNewBlock(ExecutingBlock executingBlock) {
//        Optional<Long> newTransaction = executingBlock.getTransactionId();
//        if (newTransaction.isPresent()) {
//            currentTransactionId = newTransaction;
//        }else {
//            return;
//        }
//
//
////        if (executingExecutingBlock != null) {
////            transactionExecutingBlocks.add(executingExecutingBlock);
////        }
////        executingExecutingBlock = executingBlock;
//    }
//
////    public void rollback(){
////        for (ScenarioExecutor scenarioExecutor : transactionScenarioExecutors){
////            отследить что есть сохранение ролбэка в базу при падении
////            scenarioExecutor.rollback();
////        }
////    }
//
////    public void addScenarioList(List<ExecutingBlock> executingBlockList) {
////        transactionExecutingBlocks.addAll(executingBlockList);
////    }
//
//    public void compensateNext(CompensationCallback compensationCallback, UUID scenarioId) {
//        if (compensateIterator == null) compensateIterator = transactionExecutingBlocks.iterator();
//
//        if (compensateIterator.hasNext()) {
//            ExecutingBlock compensatingExecutor = compensateIterator.next();
//            compensatingExecutor.rollback(scenarioId);
//            compensationCallback.compensated();
//        }
//    }

}
