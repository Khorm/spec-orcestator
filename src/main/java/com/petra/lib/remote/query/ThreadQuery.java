package com.petra.lib.remote.query;

import com.petra.lib.remote.query.task.InputTask;

public interface ThreadQuery {
    /**
     *
     * @param inputTask
     * @return true если задача была принята, false если задача была отклонена изза отсуствия потоков
     */
    boolean pop(InputTask inputTask);
}
