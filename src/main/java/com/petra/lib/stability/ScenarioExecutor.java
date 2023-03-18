package com.petra.lib.stability;

import com.petra.lib.variable.process.ProcessVariable;
import com.petra.lib.block.ExecutingBlock;
import com.petra.lib.workflow.graph.Graph;
import com.petra.lib.workflow.observer.ScenarioHandler;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Исполнитель графа активностей
 */
@Deprecated
public class ScenarioExecutor {
//    private static final Executor executor = Executors.newFixedThreadPool(1);
//
//
//    public void execute(Graph list, Collection<ProcessVariable> sourceValueList, UUID scenarioId, ScenarioHandler scenarioHandler) {
//        Graph.GraphIterator iterator = list.iterator();
//
//        ExecutingBlock firstBlock = list.getFirst();
//        ScenarioRunnable scenarioRunnable = new ScenarioRunnable(executor, iterator, firstBlock, scenarioId, sourceValueList, scenarioHandler);
//        executor.execute(scenarioRunnable);
//    }
//
//
//    public void compensate(List<ExecutingBlock> list, UUID scenarioId) {
//        Iterator<ExecutingBlock> iterator = list.iterator();
//
//        CompensationRunnable compensationRunnable = new CompensationRunnable(scenarioId, executor, iterator);
//        executor.execute(compensationRunnable);
//    }

}
