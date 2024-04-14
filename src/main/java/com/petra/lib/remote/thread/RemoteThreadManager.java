package com.petra.lib.remote.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RemoteThreadManager {
    private final ExecutorService remoteExecutor = Executors.newCachedThreadPool();

    public void handle(Runnable runnable){
        remoteExecutor.execute(runnable);
    }
}
