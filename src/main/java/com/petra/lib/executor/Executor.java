package com.petra.lib.executor;

public class Executor {

    private final Executor executor;


    public void execute(Runnable runnable){
        executor.execute(runnable);
    }

}
