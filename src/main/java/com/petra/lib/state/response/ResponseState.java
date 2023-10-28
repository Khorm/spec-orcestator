package com.petra.lib.state.response;

import com.petra.lib.context.ActivityContext;
import com.petra.lib.environment.output.OutputSocket;
import com.petra.lib.context.repo.ActionRepo;
import com.petra.lib.state.ActionState;
import com.petra.lib.state.StateHandler;
import com.petra.lib.transaction.TransactionManager;
import com.petra.lib.transaction.TransactionRunnable;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.orm.jpa.JpaTransactionManager;

/**
 *
 */
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Log4j2
public class ResponseState implements StateHandler {

//    Map<SignalId, VariableMapper> answerMappers;
//    String blockName;
//    ResponseReadyListener responseReadyListener;
//    BlockId blockId;
//    JobRepository jobRepository;

    OutputSocket outputSocket;
    TransactionManager transactionManager;
    ActionRepo actionRepo;

//    @Override
//    public void execute(ActionContext actionContext) throws Exception {
//        log.debug("START ResponseState {} {}", blockName, actionContext.getScenarioId());
//        UUID scenarioId = actionContext.getScenarioId();
//        VariableMapper mapperForResponseSignal = answerMappers.get(actionContext.getRequestSignalId());
//        if (actionContext.isAlreadyDone()){
//            Collection<ProcessValue> answerVariableList = mapperForResponseSignal.map(jobRepository.getVariables(actionContext.getScenarioId(), blockId));
//            responseReadyListener.idempotencyErrorToRequest(answerVariableList, actionContext.getRequestSignalId(), actionContext.getRequestBlockId(), scenarioId, blockId);
//        }else {
//            Collection<ProcessValue> answerVariableList = mapperForResponseSignal.map(actionContext.getProcessVariables());
//            responseReadyListener.answerToRequest(answerVariableList, responseSignalId, actionContext.getRequestBlockId(), scenarioId, blockId);
//        }
//        log.debug("END ResponseState {} {}", blockName, actionContext.getScenarioId());
//    }
//
//    @Override
//    public JobState getManagerState() {
//        return JobState.EXECUTION_RESPONSE;
//    }

    @Override
    public void execute(ActivityContext context) throws Exception {

        transactionManager.executeInTransaction(new TransactionRunnable() {
            @Override
            public void run(JpaTransactionManager jpaTransactionManager) {
                actionRepo.updateActionType(context.getBusinessId(), context.getCurrentBlockId(),
                        getState());
            }
        });
        outputSocket.answer(context, context.getRequestType().getAnswerType());

    }

    @Override
    public void start() {
    }

    @Override
    public ActionState getState() {
        return ActionState.END;
    }
}
