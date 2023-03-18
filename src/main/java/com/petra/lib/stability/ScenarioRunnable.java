package com.petra.lib.stability;

import com.petra.lib.variable.process.ProcessVariable;
import com.petra.lib.block.ExecutingBlock;
import com.petra.lib.workflow.graph.Graph;
import com.petra.lib.workflow.observer.ScenarioMessage;
import com.petra.lib.workflow.observer.ScenarioObserverManager;
import com.petra.lib.workflow.enums.ScenarioSignal;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Executor;

/**
 * Исполнитель сценария
 */
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class ScenarioRunnable /*implements Runnable*/{

//    /**
//     * Потоковый исполнитель
//     */
//    Executor executor;
//
//    /**
//     * итератор графа исполнителя
//     */
//    Graph.GraphIterator iterator;
//
//    /**
//     * Ияполняемый блок
//     */
//    ExecutingBlock executingBlock;
//
//    /**
//     * Айди сценария
//     */
//    UUID scenarioId;
//
//    /**
//     * Переменые вызова исполнительного блока
//     */
//    Collection<ProcessVariable> sourceValueList;
//
//    /**
//     * Слушатель результата обработки сценария
//     */
//    ScenarioObserverManager scenarioObserverManager;
//
//    @Override
//    public void run() {
//        executingBlock.execute(sourceValueList, scenarioId,
//                executionResult -> {
////                    scenarioHandler.handle(executingBlock, executionResult);
////                    executor.execute(this);
//                    List<ExecutingBlock> nextBlocks = iterator.next();
//                    ScenarioMessage scenarioMessage = ScenarioMessage.handledScenarioMessage(executingBlock, nextBlocks, executionResult)
//                    scenarioObserverManager.execute(scenarioId, 0, ScenarioSignal.HANDLED, );
//
//                    if (nextBlocks.isEmpty()){
//                        scenarioHandler.finish(executingBlock, executionResult);
//                    }else {
//                        for (ExecutingBlock executingBlock: nextBlocks){
//                            ScenarioRunnable nextScenarioRunnable = new ScenarioRunnable(executor, iterator,executingBlock,
//                                    scenarioId, executionResult, scenarioHandler);
//                            executor.execute(nextScenarioRunnable);
//                        }
//                    }
//                }
//                , error -> {
//                    scenarioHandler.iterationError(executingBlock, error);
//
//                });
//    }


}
