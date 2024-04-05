package com.petra.lib.action.handler.init;

import com.petra.lib.block.BlockId;
import com.petra.lib.action.new_action.ActionContext;
import com.petra.lib.action.new_action.repo.ActionRepo;
import com.petra.lib.action.new_action.ActionState;
import com.petra.lib.variable.value.VariablesContainer;
import com.petra.lib.transaction.TransactionManager;
import com.petra.lib.variable.mapper.VariableMapper;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;


/**
 * Заполняет переменные из входящего сигнала.
 */
@Log4j2
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ActionInitializer {
    /**
     * Маппер с сигнальных переменных на
     */
    VariableMapper initSignalToActionVariableMapper;

    /**
     * менеджер транзакций
     */
    TransactionManager transactionManager;
    BlockId blockId;

    ActionRepo actionRepo;

//    @Override
//    public ActionState getState() {
//        return ActionState.INITIALIZING;
//    }
//
//    @Override
//    public void execute(ActivityContext context) throws Exception {
//        log.debug("Start initialization {}", blockManager.getName());
//
//        //получить переменные из входящего сигнала
//        VariablesContainer variablesFromSignal
//                = initSignalToActionVariableMapper.map(context.getRequestSignalVariables());
//
//
//        //записать измененеие стейта и обновление переменных в базу.
//        transactionManager.executeInTransaction(jpaTransactionManager -> {
//            context.setNewState(ActionState.INITIALIZING);
//            context.addVariables(variablesFromSignal);
//        });
//        blockManager.execute(context);
//    }
//
//    @Override
//    public void start() {
//
//    }

    public boolean initialize(ActionContext context) {
        //получить переменные из входящего сигнала
        VariablesContainer variablesFromSignal
                = initSignalToActionVariableMapper.map(context.getInputSignalVariables());

        //записать измененеие стейта и обновление переменных в базу
        context.setNewState(ActionState.INITIALIZING);
        context.addVariables(variablesFromSignal);
        return transactionManager.executeInTransaction(jpaTransactionManager -> {
            actionRepo.updateActionContextVariables(context.getScenarioId(), blockId, context.getExecutingVariables());
            return actionRepo.updateActionContextStatus(context.getScenarioId(), blockId, context.getActionState());
        });
    }
}
