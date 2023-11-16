package com.petra.lib.state.response;

import com.petra.lib.context.ActivityContext;
import com.petra.lib.remote.dto.SignalDTO;
import com.petra.lib.remote.input.InputExecuteController;
import com.petra.lib.remote.output.http.AnswerDecoder;
import com.petra.lib.remote.output.http.SignalEncoder;
import com.petra.lib.remote.signal.SignalType;
import com.petra.lib.state.ActionState;
import com.petra.lib.state.StateHandler;
import feign.Feign;

import static com.petra.lib.state.ActionState.COMPLETION;

class SourceResponseState implements StateHandler {

    @Override
    public void execute(ActivityContext context) throws Exception {
        String requestServiceName = context.getRequestServiceName();
        InputExecuteController executeController = createController(requestServiceName);

        executeController.execute(SignalDTO.builder()
                .scenarioId(context.getScenarioId())
                .consumerBlockId(context.getRequestBlockId())
                .signalVariablesContainer(context.getContextVariablesContainer())
                .producerServiceName(context.getCurrentServiceName())
                .producerBlockId(context.getCurrentBlockId())
                .signalType(SignalType.RESPONSE_SOURCE)
                .build());
    }

    @Override
    public void start() {

    }

    @Override
    public ActionState getState() {
        return COMPLETION;
    }

    private InputExecuteController createController(String requestServiceName) {
        return Feign.builder()
                .encoder(new SignalEncoder())
                .decoder(new AnswerDecoder())
                .target(InputExecuteController.class, "http://" + requestServiceName + "/execute");
    }
}
