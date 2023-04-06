package com.petra.lib.workflow.new_workflow.graph;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ExecutionGraphImpl implements ExecutionGraph {

    Map<Long, ExecutionNode> executionNodeMap;
    Map<Long, Set<Long>> listenersByParents;
    Long startNodeId;


    ExecutionGraphImpl(Collection<ExecutionNode> executionNodes, Collection<ExecutionModel> executionModels) {
        executionNodeMap = executionNodes.stream().
                collect(Collectors.toMap(ExecutionNode::getId, Function.identity()));

        listenersByParents = new HashMap<>();
        Long startNode = null;
        for (ExecutionModel executionModel : executionModels){
            if (executionModel.getNoParents()){
                startNode = executionModel.getId();
            }
            listenersByParents.put(executionModel.getId(), new HashSet<>(executionModel.getChildren()));

        }
        this.startNodeId = startNode;

    }


    @Override
    public Collection<ExecutionNode> getNextExecutions(Long nodeId) {
        if (nodeId == null) return Collections.singletonList(executionNodeMap.get(startNodeId));

        return listenersByParents.get(nodeId).stream()
                .map(executionNodeMap::get)
                .collect(Collectors.toList());
    }

    @Override
    public int getNodesSize() {
        return executionNodeMap.size();
    }
}
