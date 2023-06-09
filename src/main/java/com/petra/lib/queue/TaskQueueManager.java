package com.petra.lib.queue;

public interface TaskQueueManager {
    void executeTask(Runnable queueTask);
}
