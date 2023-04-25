package com.petra.lib.workflow.new_workflow;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class WorkflowState /*implements ExecutionStateManager, SignalObserver*/ {

//    ExecutionHandler executionHandler;
//    ExecutionGraph executionGraph;
//    WorkflowRepo workflowRepo;
//    Long workflowId;
//
//    @Override
//    public void execute(ExecutionContext executionContext) throws Exception {
//        Collection<ExecutionNode> executionNodes = executionGraph.getNextExecutions(null);
//        executionNodes.forEach(executionNode -> executionNode.execute(executionContext.getVariablesList(), executionContext.getScenarioId()));
//    }
//
//    @Override
//    public ExecutionState getManagerState() {
//        return ExecutionState.EXECUTING;
//    }
//
//    @Override
//    public void start() {
//        //do nothin
//    }
//
//    @Override
//    public void executed(SignalTransferModel signalTransferModel) {
//        try {
//            workflowRepo.setNodeExecuted(signalTransferModel.getScenarioId(), workflowId, signalTransferModel.getReceiverId(),
//                    signalTransferModel.getSignalVariables());
//        } catch (JsonProcessingException e) {
//            e.printStackTrace();
//            return;
//        }
//        if (executionGraph.getNodesSize() == workflowRepo.getExecutedNodesCount(signalTransferModel.getScenarioId(),workflowId)){
//            ExecutionContext executionContext = new ExecutionContext(null,
//                    VariableMapperFactory.createDummyMapper(), signalTransferModel, entityManager);
//            executionHandler.executeNext(executionContext, getManagerState());
//        }
//
//        if (signalTransferModel.getSignalType() != SignalType.ERROR) {
//            executeNext(signalTransferModel.getScenarioId(), signalTransferModel.getReceiverId(), signalTransferModel.getSignalVariables());
//        }else {
//            System.out.println("error in " + signalTransferModel.getReceiverId());
//        }
//    }
//
//    @Override
//    public void error(Exception e, SignalTransferModel signalTransferModel) {
//        //=(
//        System.out.println(e);
//    }
//
//    private void executeNext(UUID scenarioId, Long executedNode, Collection<ProcessVariable> prevExecutionResult){
//        Collection<ExecutionNode> executionNodes = executionGraph.getNextExecutions(executedNode);
//        executionNodes.forEach(nodeToExecute ->{
//            if (!workflowRepo.isNodeExecuted(scenarioId, workflowId, nodeToExecute.getId())){
//                nodeToExecute.execute(prevExecutionResult, scenarioId);
//            }else {
//                Collection<ProcessVariable> result = null;
//                try {
//                    result = workflowRepo.getNodeExecutionResults(scenarioId, workflowId, nodeToExecute.getId());
//                } catch (JsonProcessingException e) {
//                    e.printStackTrace();
//                    return;
//                }
//                executeNext(scenarioId, nodeToExecute.getId(), result);
//            }
//        });
//    }
}
