package com.petra.lib.workflow;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

/**
 * Оркестратор локальных и удаленных активностей и других оркестраторов.
 * Не может содержать исполняемый код, исключительно управляет последовательным вызовом активностей или вложенных оркестраторов.
 */

@FieldDefaults(level = AccessLevel.PRIVATE)
public class LocalWorkflow /*extends AbsExecutingBlock*/ {

    /**
     * Граф, хранящий в себе последовательность исполнения оркестраторов и активностей
     */
//    Graph workflowsAndActionsGraph;
//    ScenarioObserverManager scenarioObserverManager;
//    List<Long> transactions;
//    Executor executor;
//
//    /**
//     * Менеджер транзакций
//     */
//    PlatformTransactionManager txManager;
//
//    WorkflowRepository workflowRepository;
//
//    public LocalWorkflow(Long blockId, List<Long> transactions) {
//        super(blockId, transactions);
//    }
//
//
//    @Override
//    public void execute(ExecutionRequest request) {
//        executor.execute(() -> {
////            проверить вызывался ли вокфлоу в базе
////            если вызывался то вернуть результат вызова
////            в ином случае продолжить вызов
//
//
////            Boolean isRequestAlreadyDone = workflowRepository.isWorkflowRequestedBefore(request.getScenarioId());
////            if (isRequestAlreadyDone){
////
////            }
//
//            callExecutingBlock(request, workflowsAndActionsGraph.getGraphIterator(),
//                    workflowsAndActionsGraph.getEntryPoint());
//        });
//
//    }
//
//    @Override
//    public void rollback(RollbackRequest rollbackRequest) {
//        executor.execute(() -> {
////            проверить исполнялся ли сценарий
////            и не был ли откачен
//            rollbackBlocks(rollbackRequest.getScenarioId(), rollbackRequest.getRollbackTransactionList());
//        });
//
//    }
//
//    @Override
//    public Version getVersion() {
//        return null;
//    }
//
//
//    private void callExecutingBlock(ExecutionRequest request, GraphIterator graphIterator, ExecutingBlock executingBlock) {
//        scenarioObserverManager.subscribe(request.getScenarioId(), executingBlock.getBlockId(), executionResponse -> {
//            switch (executionResponse.getExecutionStatus().getStatus()) {
//                case HANDLED:
//                    handleExecutionResult(executionResponse, graphIterator, executingBlock);
//                    break;
//                case ERROR:
//                    rollbackBlocks(executionResponse.getScenarioId(), executionResponse.getTransactionsId());
//                    break;
//                default:
//                    throw new IllegalArgumentException("Wrong scenario signal");
//            }
//        });
//        executingBlock.execute(request);
//    }
//
//
//    private void rollbackBlocks(UUID scenarioId, List<Long> rollbackTransactions) {
//        List<ExecutingBlock> rollbackBlocks = new ArrayList<>();
//        for (Long rollbackTransaction : rollbackTransactions) {
//            List<ExecutingBlock> blockList = workflowsAndActionsGraph.getByTransactionId(rollbackTransaction);
//            rollbackBlocks.addAll(blockList);
//        }
//        for (ExecutingBlock block : rollbackBlocks) {
//            RollbackRequest rollbackRequest = new RollbackRequest(scenarioId, block.getBlockId(), rollbackTransactions);
//            block.rollback(rollbackRequest);
//        }
//
//        boolean findTransaction = false;
//        Set<Long> compareSet = new HashSet<>(transactions);
//        for (Long rollbackTransaction : rollbackTransactions) {
//            if (compareSet.contains(rollbackTransaction)) {
//                findTransaction = true;
//                break;
//            }
//        }
//        if (findTransaction) {
////            записать в базу что исполнение закончилось ошибкой
////            сообщить об ошибке исполнения
//        } else {
////            записать в базу что исполнение закончилось успешно
////            сообщить об успешном исполнении
//        }
//    }
//
//    private void handleExecutionResult(ExecutionResponse executionResponseDto, GraphIterator graphIterator, ExecutingBlock executingBlock) {
//        graphIterator.executeElement();
//        if (graphIterator.getLeftElementsSize() == 0) {
////            записать в базу что исполнение закончилось
////            выполнить сигнал об окончании исполнения
//            return;
//        }
//        List<ExecutingBlock> blocksToExecute = graphIterator.getChild(executingBlock);
//        for (ExecutingBlock nextExecutingBlock : blocksToExecute) {
//            ExecutionRequest requestDto = new ExecutionRequest(
//                    executionResponseDto.getScenarioId(),
//                    nextExecutingBlock.getVersion(),
//                    executionResponseDto.getAnswerValues(),
//                    nextExecutingBlock.getBlockId());
//            callExecutingBlock(requestDto, graphIterator, nextExecutingBlock);
//        }
//
//    }

}
