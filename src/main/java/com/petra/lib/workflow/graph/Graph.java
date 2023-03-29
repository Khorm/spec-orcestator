package com.petra.lib.workflow.graph;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class Graph {


//    регистрировать новых пользователей даже во время выполнения через кафку
//    и запрашивать заполнения при старте

//    Map<ExecutingBlock, List<ExecutingBlock>> graph = new HashMap<>();
//
//    Map<Long, List<ExecutingBlock>> transactionsMap;
//    Map<Long, ExecutingBlock> blocks;
//
//    @Getter
//    ExecutingBlock entryPoint;
//
//    long size;
//
//
//    public Graph(Set<BlockNode> nodesList){
//        this.size = nodesList.size();
//        transactionsMap = new HashMap<>();
//        ExecutingBlock firstBlock = null;
//        for (BlockNode blockNode: nodesList){
//            if (blockNode.isEntryPoint()){
//                if (firstBlock != null){
//                    throw new IllegalStateException("More than one entrypoints");
//                }
//                firstBlock = blockNode.getBlock();
//            }
//
//            for (BlockNode parent : blockNode.getParents()) {
//                if (!graph.containsKey(parent)){
//                    graph.put(parent.getBlock(), new CopyOnWriteArrayList<>());
//                }
//                List<ExecutingBlock> childList = graph.get(parent.getBlock());
//                childList.add(blockNode.getBlock());
//
//                graph.put(blockNode.getBlock(), new ArrayList<>());
//            }
//
//            for (Long blockNodeTransaction : blockNode.getBlock().getTransactionsId()){
//                if (!transactionsMap.containsKey(blockNodeTransaction)){
//                    transactionsMap.put(blockNodeTransaction, new ArrayList<>());
//                }
//                List<ExecutingBlock> transactionsList = transactionsMap.get(blockNodeTransaction);
//                transactionsList.add(blockNode.getBlock());
//            }
//        }
//        this.entryPoint = firstBlock;
//    }
//
//    public GraphIterator getGraphIterator(){
//        return new GraphIterator(graph);
//    }
//
//
//    public List<ExecutingBlock> getChildList(ExecutingBlock parentBlock){
//        return graph.get(parentBlock);
//    }
//
//    /**
//     * @return
//     * Size of all executing blocks in graph
//     */
//    public long size(){
//        return size;
//    }
//
//    public List<ExecutingBlock> getByTransactionId(Long transactionId){
//        return transactionsMap.get(transactionId);
//    }
//
//    public ExecutingBlock getBlockById(Long id){
//        return blocks.get(id);
//    }


}
