package com.petra.lib.state.variable.group.repo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.petra.lib.context.ExecutionContext;

import java.util.Collection;
import java.util.Set;
import java.util.UUID;

public interface ContextRepo {

    /**
     * Register new context
     *
     * @param context
     * @throws JsonProcessingException
     */
    void addNewRequestData(ExecutionContext context) throws JsonProcessingException;

    /**
     * Clear context
     *
     * @param scenarioId
     * @param blockId
     * @return
     */
    ContextModel clear(UUID scenarioId);

    /**
     * Set changing context
     *
     * @param filledVariableId - id of filled variable
     * @return Set counts
     */
    Set<Long> setFilledVariables(Collection<Long> filledVariableId, UUID scenarioId);

    ExecutionContext getExecutionContext(UUID scenarioId);

    /**
     * Save already filled group id
     * @param groupId
     * @return all filled groups
     */
    Set<Long> setFilledGroup(Long groupId, UUID scenarioId);

    static ContextRepo getLocalContextRepo() {
        return new LocalContextRepo();
    }

}
