package com.petra.lib.query;

public interface ThreadQuery {
    /**
     *
     * @param inputTask
     * @return true если задача была принята, false если задача была отклонена изза отсуствия потоков
     */
    boolean forcedPop(InputTask inputTask);

    /**
     * Поместить задачу в очередь
     * @param inputTask - задача
     */
    void popInQueue(InputTask inputTask);

//    void popInQueueAndWait(InputTask inputTask);
}
