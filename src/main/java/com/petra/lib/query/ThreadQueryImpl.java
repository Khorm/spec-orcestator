package com.petra.lib.query;

import lombok.extern.log4j.Log4j2;

import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@Log4j2
class ThreadQueryImpl implements ThreadQuery {
    private final List<Thread> threadList = new ArrayList<>();
    private final BlockingQueue<InputTask> blockingQueue = new LinkedBlockingQueue<>();

    ThreadQueryImpl(int threadSize){
        for (int i = 0; i < threadSize; i++){
            Thread thread = new Thread(() -> {
                while (true){
                    try {
                        InputTask inputTask = blockingQueue.take();
                        inputTask.run();
                    } catch (InterruptedException e) {
                        log.error(e);
                    }
                }
            });
            threadList.add(thread);
            thread.start();
        }
    }


    @Override
    public boolean forcedPop(InputTask inputTask) {
       return blockingQueue.offer(inputTask);
    }

    @Override
    public void popInQueue(InputTask inputTask) {
        try {
            blockingQueue.put(inputTask);
        } catch (InterruptedException e) {
            //never thrown
            throw new RuntimeException(e);
        }
    }

}
