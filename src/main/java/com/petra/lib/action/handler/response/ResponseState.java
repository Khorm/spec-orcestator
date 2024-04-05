package com.petra.lib.action.handler.response;

import com.petra.lib.action.new_action.ActionContext;
import com.petra.lib.action.new_action.repo.ActionRepo;
import com.petra.lib.variable.value.VariablesContainer;
import com.petra.lib.remote.response.Response;
import com.petra.lib.remote.response.ResponseSignal;
import com.petra.lib.remote.response.ResponseSignalType;
import com.petra.lib.action.new_action.ActionState;
import com.petra.lib.transaction.TransactionManager;
import com.petra.lib.variable.mapper.VariableMapper;
import com.petra.lib.workflow.SignalId;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 */
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Log4j2
public class ResponseState{

    Response response;
    TransactionManager transactionManager;
    ActionRepo actionRepo;

    /**
     * Мапа мапперов исходящих сигналов
     */
    Map<SignalId, VariableMapper> outputSignalMappers;

    public void execute(ActionContext context) throws Exception {
        context.setNewState(ActionState.COMPLETE);
        boolean transactionResult = transactionManager.executeInTransaction(jpaTransactionManager -> {
            return actionRepo.updateActionContextStatus(context.getScenarioId(), context.getActionId(), context.getActionState());
        });
        if (!transactionResult) return;

        List<ResponseSignal> responseSignals = new ArrayList<>();
        VariablesContainer outputSignalVariables = outputSignalMappers.get(context.getOutputSignalId())
                .map(context.getExecutingVariables());
        responseSignals.add(new ResponseSignal(context.getOutputSignalId(), outputSignalVariables));

        response.response(
                context.getScenarioId(),
                context.getActionId(),
                responseSignals,
                ResponseSignalType.ACTION_COMPLETE,
                context.getRequestBlockId(),
                context.getRequestServiceName()
        );
    }
}
