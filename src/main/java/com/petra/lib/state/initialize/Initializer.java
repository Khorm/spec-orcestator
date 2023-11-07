package com.petra.lib.state.initialize;

import com.petra.lib.block.Block;
import com.petra.lib.context.ActivityContext;
import com.petra.lib.context.state.ActionState;
import com.petra.lib.context.variables.VariablesContainer;
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
    VariableMapper initSignalToActionVariableMapper;

    /**
     * Блок управления активностью
     */
    Block blockManager;

    /**
     * менеджер транзакций
     */
    TransactionManager transactionManager;

    @Override
    public ActionState getState() {
        return ActionState.INITIALIZING;
    }

    @Override
    public void execute(ActivityContext context) throws Exception {
        log.debug("Start initialization {}", blockManager.getName());

        //получить переменные из входящего сигнала
        VariablesContainer variablesFromSignal
                = initSignalToActionVariableMapper.map(context.getSignalVariables());


        //записать измененеие стейта и обновление переменных в базу.
        transactionManager.commitInTransaction(jpaTransactionManager -> {
            context.setNewState(ActionState.INITIALIZING);
            context.addVariables(variablesFromSignal);
        });
        blockManager.execute(context);
    }

    @Override
    public void start() {

    }
}
