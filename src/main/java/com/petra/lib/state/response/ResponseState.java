package com.petra.lib.state.response;

import com.petra.lib.context.ActivityContext;
import com.petra.lib.remote.output.manager.SignalRequestStrategy;
import com.petra.lib.state.ActionState;
import com.petra.lib.context.value.VariablesContainer;
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
class ResponseState implements StateHandler {

    SignalRequestStrategy outputSocket;
    VariableMapper contextToSourceMapper;

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
        outputSocket.send()

//        Signal answerSignal = new Signal(
//                context.getScenarioId(),
//                sendSignalVariables, context.getRequestSignalName(), context.getRequestSignalId(),
//                context.getRequestServiceName(), context.getRequestBlockId(),
//                context.getCurrentServiceName(), context.getCurrentBlockId(),
//                answerSignalType
//        );
//        outputSocket.answer(answerSignal);
    }

    @Override
    public void start() {
    }

    @Override
    public ActionState getState() {
        return ActionState.COMPLETION;
    }
}
