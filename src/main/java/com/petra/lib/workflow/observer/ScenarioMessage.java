package com.petra.lib.workflow.observer;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ScenarioMessage {
//    Optional<ExecutingBlock> prevBlock;
//    List<ExecutingBlock> nextBlocks;
//    Optional<Exception> exception;
//    Collection<ProcessVariable> result;
//    Optional<Long> transactionId;
//    UUID scenarioId;
//
//    private ScenarioMessage(Optional<ExecutingBlock> prevBlock, List<ExecutingBlock> nextBlocks,
//                            Optional<Exception> exception, Collection<ProcessVariable> result, Optional<Long> transactionId, UUID scenarioId){
//        this.prevBlock = prevBlock;
//        this.nextBlocks = nextBlocks;
//        this.exception = exception;
//        this.result = result;
//        this.transactionId = transactionId;
//        this.scenarioId = scenarioId;
//    }
//
//    public static ScenarioMessage handledScenarioMessage(ExecutingBlock prevBlock, List<ExecutingBlock> nextBlocks,
//                                                         Collection<ProcessVariable> result, Long transactionId, UUID scenarioId){
//
//        Optional<ExecutingBlock> prevBlockOpt = Optional.ofNullable(prevBlock);
//        Optional<Exception> exceptionOpt = Optional.empty();
//        Optional<Long> transactionIdOpt = Optional.ofNullable(transactionId);
//
//        return new ScenarioMessage(prevBlockOpt, nextBlocks, exceptionOpt, result, transactionIdOpt, scenarioId);
//
//    }


}
