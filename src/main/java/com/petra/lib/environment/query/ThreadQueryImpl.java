package com.petra.lib.environment.query;

import com.petra.lib.block.VersionBlockId;
import com.petra.lib.environment.query.task.InputTask;
import lombok.extern.log4j.Log4j2;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Log4j2
class ThreadQueryImpl implements ThreadQuery {

    private final Set<VersionBlockId> currentExecutedSets = Collections.synchronizedSet(new HashSet<>());

    /**
     * ќчередность выстраиваетс€ только по блокам
     */
    private final ExecutorService executorService = Executors.newFixedThreadPool(4);

    @Override
    public void pop(InputTask inputTask) {
        executorService.execute(inputTask);
    }

    private static class task implements Runnable {
        InputTask task;
        ThreadQueryImpl threadQuery;

        @Override
        public void run() {
            if (!task.isSequentially()) {
                task.run();
                return;
            }
            boolean added = threadQuery.currentExecutedSets.add(task.getBlockId());
            log.debug("Block with id {} already in action", task.getBlockId());
            if (added) {
                task.run();
                threadQuery.currentExecutedSets.remove(task.getBlockId());
            } else {
                threadQuery.pop(task);
            }
        }
    }
}
