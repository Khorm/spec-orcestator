package com.petra.lib.workflow.new_workflow.graph;

import java.util.Collection;

public interface ExecutionGraph {

    Collection<ExecutionNode> getNextExecutions(Long nodeId);
    int getNodesSize();
}
