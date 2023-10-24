package com.petra.lib.state.initialize;

import com.petra.lib.environment.context.variables.VariablesContext;
import com.petra.lib.block.Block;
import com.petra.lib.environment.context.ActivityContext;
import com.petra.lib.environment.repo.ActionRepo;
import com.petra.lib.state.ActionState;
import com.petra.lib.state.StateHandler;
import com.petra.lib.transaction.TransactionManager;
import com.petra.lib.variable.mapper.VariableMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;

/**
 * Заполняет переменные из входящего сигнала.
 */
@Log4j2
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class Initializer implements StateHandler {
    /**
     * Маппер с сигнальных переменных на
     */
    VariableMapper signalToActionVariableMapper;
    Block blockManager;

    /**
     * менеджер транзакций
     */
    TransactionManager transactionManager;
    ActionRepo actionRepo;
    @Override
    public ActionState getState() {
        return ActionState.INITIALIZING;
    }

    @Override
    public void execute(ActivityContext context) throws Exception {
        log.debug("Start initialization {}", context.getCurrentBlockId());

        //обновить переменные из входящего сигнала
        VariablesContext variablesFrommSignal
                = signalToActionVariableMapper.map(context.getInitializingSignal().getVariablesContext());
        context.syncCurrentInputVariableList(variablesFrommSignal);

        //записать измененеие стейта и обновление переменных в базу.
        transactionManager.executeInTransaction(jpaTransactionManager -> {
            actionRepo.updateActionState(context, ActionState.INITIALIZING);
            actionRepo.updateVariables(context, context.getVariablesContext());
        });
        blockManager.execute(context);
    }

    @Override
    public void start() {

    }
}
