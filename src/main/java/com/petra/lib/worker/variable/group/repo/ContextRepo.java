package com.petra.lib.worker.variable.group.repo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.petra.lib.manager.block.BlockId;
import com.petra.lib.manager.block.JobContext;

import java.util.Set;
import java.util.UUID;

public interface ContextRepo {

    /**
     * Register new context
     *
     * @param context
     * @throws JsonProcessingException
     */
    void addNewRequestData(JobContext context) throws JsonProcessingException;

    /**
     * Clear context
     *
     * @param scenarioId
     * @param blockId
     * @return
     */
    ContextModel clear(UUID scenarioId, BlockId blockId);

    /**
     * Set changing context
     *
     * @param context
     * @return Set counts
     */
    Set<Long> setExecution(JobContext context, Long filledGroupId);

    JobContext getExecutionContext(UUID scenarioId, BlockId blockId);

    static ContextRepo getLocalContextRepo(){
        return new LocalContextRepo();
    }

}
