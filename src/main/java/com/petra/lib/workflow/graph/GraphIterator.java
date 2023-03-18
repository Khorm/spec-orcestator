package com.petra.lib.workflow.graph;

import com.petra.lib.block.ExecutingBlock;

import java.util.List;
import java.util.Map;

public class GraphIterator {

    Map<ExecutingBlock, List<ExecutingBlock>> graph;

    ThreadLocal<Long> elementsExecuted = new ThreadLocal<>();

    GraphIterator(Map<ExecutingBlock, List<ExecutingBlock>> graph){
        this.graph = graph;
        elementsExecuted.set(0l);
    }

    public List<ExecutingBlock> getChild(ExecutingBlock parentBlock){
        return graph.get(parentBlock);
    }

    public void executeElement(){
        elementsExecuted.set(elementsExecuted.get()+1);
    }

    public Long getLeftElementsSize(){
        return graph.size() - elementsExecuted.get();
    }


}
