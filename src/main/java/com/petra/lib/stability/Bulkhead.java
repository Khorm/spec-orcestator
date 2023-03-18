package com.petra.lib.stability;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Deprecated
public class Bulkhead {
    static final Executor executor = Executors.newCachedThreadPool();

    public static void execute(Runnable runnable){
        executor.execute(runnable);
    }


}
