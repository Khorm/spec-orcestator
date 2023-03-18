package com.petra.lib.manager;

import com.petra.lib.executor.Executor;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ThreadManager {
    private final ExecutorService executor;

    public ThreadManager(int executorsSize){
        executor = Executors.newFixedThreadPool(executorsSize);
    }

    public void execute(Runnable runnable){
        execute(runnable);
    }

    public void executeAndWait(Runnable runnable) throws ExecutionException, InterruptedException {
        Future future = executor.submit(runnable);
        future.get();
    }
}
