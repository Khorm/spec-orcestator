package com.petra.lib.source.local;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class LocalSource /*extends AbsExecutingBlock*/ {

//    VariableMapCollection variableMapCollection;
//    SourceHandler sourceHandler;
//    Collection<ConsumerSignal> consumerSignals;
//
//    public LocalSource(Long blockId, List<Long> transactions, Version version,
//                       VariableMapCollection variableMapCollection, SourceHandler sourceHandler) {
//        super(blockId, transactions, version);
//        this.variableMapCollection = variableMapCollection;
//        this.sourceHandler = sourceHandler;
//    }
//
//    @Override
//    public ExecutionResponse execute(ExecutionRequest request) {
//        try {
//            ProcessVariablesCollection processVariablesCollection = new ProcessVariablesCollection(getBlockId(), variableMapCollection);
//            for (ProcessVariable processVariable : request.getValueList()) {
//                processVariablesCollection.putProcessVariable(processVariable);
//            }
//
//            SourceContextImpl sourceContext = new SourceContextImpl(variableMapCollection, processVariablesCollection);
//            sourceHandler.execute(sourceContext);
//
//            ExecutionStatus executionStatus = new ExecutionStatus(ScenarioSignal.HANDLED, null);
//            return new ExecutionResponse(
//                    request.getScenarioId(),
//                    getVersion(),
//                    sourceContext.getProcessVariables(),
//                    getBlockId(),
//                    executionStatus,
//                    Collections.emptyList()
//            );
//        }catch (Exception e){
//            ExecutionStatus executionStatus = new ExecutionStatus(ScenarioSignal.ERROR, e.getClass().getName() + " : " + e.getMessage());
//            return new ExecutionResponse(
//                    request.getScenarioId(),
//                    getVersion(),
//                    null,
//                    getBlockId(),
//                    executionStatus,
//                    Collections.emptyList()
//            );
//        }
//    }
//
//    @Override
//    public void rollback(RollbackRequest rollbackRequest) {
//        throw new UnsupportedOperationException("Source cannot compensate");
//    }
}
