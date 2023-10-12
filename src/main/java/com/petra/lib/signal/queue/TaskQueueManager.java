package com.petra.lib.signal.queue;

public interface TaskQueueManager {
    void executeTask(Runnable queueTask);
}
