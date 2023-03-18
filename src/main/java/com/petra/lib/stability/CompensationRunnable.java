package com.petra.lib.stability;

import com.petra.lib.block.ExecutingBlock;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.Iterator;
import java.util.UUID;
import java.util.concurrent.Executor;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class CompensationRunnable /*implements Runnable*/ {
//
//    UUID scenarioId;
//    Executor executor;
//    Iterator<ExecutingBlock> iterator;
//
//    @Override
//    public void run() {
//        ExecutingBlock compensationBlock = iterator.next();
//        compensationBlock.rollback(scenarioId,
//                executionResult -> {
//                    if (iterator.hasNext()) {
//                        CompensationRunnable compensationRunnable = new CompensationRunnable(scenarioId, executor, iterator);
//                        executor.execute(compensationRunnable);
//                    }
//
//                }
//                , error -> {
//                    if (iterator.hasNext()) {
//                        CompensationRunnable compensationRunnable = new CompensationRunnable(scenarioId, executor, iterator);
//                        executor.execute(compensationRunnable);
//                    }
//                });
//    }
}
