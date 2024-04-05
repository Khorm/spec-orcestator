package com.petra.lib.workflow.repo;

import com.petra.lib.block.BlockId;
import com.petra.lib.workflow.signal.WorkflowContext;

import java.util.Optional;
import java.util.UUID;

class WorkflowRepoImpl implements WorkflowRepo{
    @Override
    public Optional<WorkflowContext> findContext(UUID scenarioId, BlockId workflowId) {
        return Optional.empty();
    }

    @Override
    public void save(WorkflowContext workflowContext) {

    }
}
