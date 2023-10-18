package com.petra.lib.state.initialize;

import com.petra.lib.XXXXXcontext.DirtyVariablesList;
import com.petra.lib.block.Block;
import com.petra.lib.block.BlockId;
import com.petra.lib.environment.model.ScenarioContext;
import com.petra.lib.environment.repo.ActionRepo;
import com.petra.lib.state.State;
import com.petra.lib.state.StateHandler;
import com.petra.lib.transaction.TransactionManager;
import com.petra.lib.transaction.TransactionRunnable;
import com.petra.lib.variable.mapper.VariableMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.orm.jpa.JpaTransactionManager;

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

//    public Initializer(BlockModel blockModel, StateManager stateManager, JobRepository jobRepository, VariableMapper signalToActionVariableMapper) {
//        this.blockId = new BlockId(blockModel.getId(), blockModel.getVersion());
//        this.blockName = blockModel.getName();
//        this.stateManager = stateManager;
//        this.jobRepository = jobRepository;
//        this.signalToActionVariableMapper = signalToActionVariableMapper;
//    }

//    @Override
//    public void execute(DirtyContext context) throws JsonProcessingException {
//        //проверить была ли раьше вызвана данная обработка
//        Optional<ExecutionStatus> prevStatus = jobRepository.isExecutedBefore(context.getScenarioId(), blockId);
//
//        if (prevStatus.isPresent()) {
//            ExecutionStatus curStatus = prevStatus.get();
//
//            switch (curStatus) {
//                //стандартное прожолжение исплнения  активности
//                case STARTED:
//                    jobRepository.setExecutingStatus(ExecutionStatus.EXECUTING);
//                    log.debug("Job starts executed {} {}", blockName, context.toString());
//                    stateManager.executedState(context, State.INITIALIZING);
//                    break;
//
//                //активность либо была исполнена ранее либо исполняется сейчас.
//                //закончить выполнение активности путем высавление ошибки
//                case EXECUTED:
//                case EXECUTING:
//                    log.debug("Job already executed {} {}", blockName, context.toString());
//                    stateManager.executedState(context, State.INITIALIZING, StateError.ALREADY_EXECUTING);
//                    break;
//
//                case ERROR:
//                    String error = jobRepository.getErrorMessage(context.getScenarioId(), blockId);
//                    log.debug("Job already end with error {} {} : {}", blockName, context.toString(), error);
//                    stateManager.executedState(context, State.INITIALIZING, StateError.INNER_ERROR);
//                    break;
//            }
//        }
//        throw new IllegalStateException("Execution status not found");
//    }

    @Override
    public State getState() {
        return State.INITIALIZING;
    }

    @Override
    public void execute(ScenarioContext context) throws Exception {
        log.debug("Start initialization {}", context.getBlockId());

        //обновить переменные из входящего сигнала
        DirtyVariablesList variablesFrommSignal
                = signalToActionVariableMapper.map(context.getSignal().getDirtyVariablesList());
        context.syncCurrentInputVariableList(variablesFrommSignal);

        //записать измененеие стейта и обновление переменных в базу.
        transactionManager.executeInTransaction(jpaTransactionManager -> {
            actionRepo.updateActionState(context, State.INITIALIZING);
            actionRepo.updateVariables(context, context.getDirtyVariablesList());
        });
        blockManager.execute(context);
    }

    @Override
    public void start() {

    }
}
