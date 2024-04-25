package com.petra.lib.query;

public final class ThreadQueryFactory {

    public static ThreadQuery createThreadQuery(int threadSize){
        return new ThreadQueryImpl(threadSize);
    }
}
