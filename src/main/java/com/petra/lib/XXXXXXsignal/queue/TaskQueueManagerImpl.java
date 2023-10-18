package com.petra.lib.XXXXXXsignal.queue;

import com.petra.lib.block.Block;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;

import java.util.Map;
import java.util.concurrent.*;

/**
 * Execute manager of all block tasks in the service.
 */
@Log4j2
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
class TaskQueueManagerImpl implements TaskQueueManager {
    ExecutorService jobExecutor;
    Map<Long, Block> blockMap;

    TaskQueueManagerImpl(int threadsSize, Map<Long, Block> blockMap) {
        //TODO: протетстить будет ли блокироваться очередь при submit
        jobExecutor = new ThreadPoolExecutor(threadsSize, threadsSize, 0l, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(threadsSize),
                new RejectedExecutionHandler() {
                    @Override
                    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                        if (!executor.isShutdown()) {
                            try {
                                executor.getQueue().put(r);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
        this.blockMap = blockMap;
    }

    @Override
    public void executeTask(Runnable queueTask) {
        jobExecutor.execute(queueTask);
    }

}
