package com.petra.lib.action.handler.error;

import com.petra.lib.action.new_action.ActionContext;
import com.petra.lib.action.new_action.ActionState;
import com.petra.lib.action.new_action.repo.ActionRepo;
import com.petra.lib.remote.response.Response;
import com.petra.lib.remote.response.ResponseSignalType;
import com.petra.lib.transaction.TransactionManager;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;

@Log4j2
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class ErrorState {

//    @Override
//    public void execute(ActionContext context) throws Exception {
//        String requestServiceName = context.getRequestServiceName();
//        InputExecuteController executeController = createController(requestServiceName);
//
//        executeController.execute(SignalDTO.builder()
//                .scenarioId(context.getScenarioId())
//                .consumerBlockId(context.getRequestBlockId())
//                .producerServiceName(context.getCurrentServiceName())
//                .producerBlockId(context.getCurrentBlockId())
//                .signalType(SignalType.ERROR)
//                .build());
//    }
//
//    @Override
//    public void start() {
//
//    }
//
//    @Override
//    public ActionState getState() {
//        return ActionState.ERROR;
//    }
//
//    private InputExecuteController createController(String requestServiceName) {
//        return Feign.builder()
//                .encoder(new SignalEncoder())
//                .decoder(new AnswerDecoder())
//                .target(InputExecuteController.class, "http://" + requestServiceName + "/execute");
//    }

    TransactionManager transactionManager;
    ActionRepo actionRepo;
    Response response;


    public void execute(ActionContext context) {
        context.setNewState(ActionState.ERROR);
        boolean transactionResult = transactionManager.executeInTransaction(jpaTransactionManager -> {
            return actionRepo.updateActionContextStatus(context.getScenarioId(), context.getActionId(), context.getActionState());
        });
        if (!transactionResult) return;

//        List<ResponseSignal> responseSignals = new ArrayList<>();
//        VariablesContainer outputSignalVariables = outputSignalMappers.get(context.getOutputSignalId())
//                .map(context.getExecutingVariables());
//        responseSignals.add(new ResponseSignal(context.getOutputSignalId(), outputSignalVariables));

        response.response(
                context.getScenarioId(),
                context.getActionId(),
                //нужен айди входящего сигнала чтобы понять на какой конкретно сигнал была ошибка,
                ResponseSignalType.ERROR,
                context.getRequestBlockId(),
                context.getRequestServiceName()
        );
    }
}
