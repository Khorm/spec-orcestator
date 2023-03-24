package com.petra.lib.manager;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadManager {
    private final ExecutorService executor;

    public ThreadManager(int executorsSize) {
        executor = Executors.newFixedThreadPool(executorsSize);
    }

    public void execute(Runnable runnable) {
        executor.execute(runnable);
    }

//    public void executeAndWait(Runnable runnable) throws ExecutionException, InterruptedException {
//        Future future = executor.submit(runnable);
//        future.get();
//    }
}
