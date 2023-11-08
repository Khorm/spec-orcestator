package com.petra.lib.state.response;

import com.petra.lib.context.ActivityContext;
import com.petra.lib.state.ActionState;
import com.petra.lib.variable.container.VariablesContainer;
import com.petra.lib.remote.output.OutputAnswerSocket;
import com.petra.lib.remote.signal.Signal;
import com.petra.lib.remote.signal.SignalType;
import com.petra.lib.state.StateHandler;
import com.petra.lib.variable.mapper.VariableMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;

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

    OutputAnswerSocket outputSocket;
    //    TransactionManager transactionManager;
    VariableMapper contextToSourceMapper;

//    ActionRepo actionRepo;

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
        SignalType answerSignalType;
        switch (context.getRequestSignalType()) {
            case REQUEST_ACTIVITY_EXECUTION:
                answerSignalType = SignalType.RESPONSE_ACTIVITY_EXECUTED;
                break;

            case REQUEST_ROLLOUT:
                answerSignalType = SignalType.RESPONSE_ROLLOUT;
                break;

            case REQUEST_SOURCE:
                answerSignalType = SignalType.RESPONSE_SOURCE;
                break;
            default:
                throw new IllegalStateException("Wrong state " + context.getState());
        }

        context.setNewState(getState());
        VariablesContainer sendSignalVariables = contextToSourceMapper.map(context.getContextVariablesContainer());

        Signal answerSignal = new Signal(
                context.getScenarioId(),
                sendSignalVariables, context.getRequestSignalName(), context.getRequestSignalId(),
                context.getRequestServiceName(), context.getRequestBlockId(),
                context.getCurrentServiceName(), context.getCurrentBlockId(),
                answerSignalType
        );
        outputSocket.answer(answerSignal);
    }

    @Override
    public void start() {
    }

    @Override
    public ActionState getState() {
        return ActionState.COMPLETION;
    }
}
