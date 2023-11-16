package com.petra.lib.state.response;

import com.petra.lib.context.ActivityContext;
import com.petra.lib.remote.output.http.SignalManager;
import com.petra.lib.state.ActionState;
import com.petra.lib.context.value.VariablesContainer;
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

    SignalManager outputSocket;

    @Override
    public void execute(ActivityContext context) throws Exception {
        context.setNewState(getState());
        outputSocket.send(context);
    }

    @Override
    public void start() {
    }

    @Override
    public ActionState getState() {
        return ActionState.COMPLETION;
    }
}
