package com.petra.lib.manager.block;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public final class ThreadManager {
    private static ExecutorService executor;

    private ThreadManager(){}

    public static void setPoolSize(int executorsSize){
        executor = Executors.newFixedThreadPool(executorsSize);
    }

    public static void execute(Runnable runnable) {
        executor.execute(runnable);
    }



}
