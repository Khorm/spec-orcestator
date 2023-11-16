package com.petra.lib.state.error;

import com.petra.lib.context.ActivityContext;
import com.petra.lib.remote.dto.SignalDTO;
import com.petra.lib.remote.input.InputExecuteController;
import com.petra.lib.remote.output.http.AnswerDecoder;
import com.petra.lib.remote.output.http.SignalEncoder;
import com.petra.lib.remote.signal.SignalType;
import com.petra.lib.state.ActionState;
import com.petra.lib.remote.output.http.request.OutputConsumeSocket;
import com.petra.lib.state.StateHandler;
import com.petra.lib.transaction.TransactionManager;
import feign.Feign;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;

@Log4j2
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class ErrorState implements StateHandler {

    @Override
    public void execute(ActivityContext context) throws Exception {
        String requestServiceName = context.getRequestServiceName();
        InputExecuteController executeController = createController(requestServiceName);

        executeController.execute(SignalDTO.builder()
                .scenarioId(context.getScenarioId())
                .consumerBlockId(context.getRequestBlockId())
                .producerServiceName(context.getCurrentServiceName())
                .producerBlockId(context.getCurrentBlockId())
                .signalType(SignalType.ERROR)
                .build());
    }

    @Override
    public void start() {

    }

    @Override
    public ActionState getState() {
        return ActionState.ERROR;
    }

    private InputExecuteController createController(String requestServiceName) {
        return Feign.builder()
                .encoder(new SignalEncoder())
                .decoder(new AnswerDecoder())
                .target(InputExecuteController.class, "http://" + requestServiceName + "/execute");
    }
}
