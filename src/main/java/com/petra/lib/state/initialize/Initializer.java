package com.petra.lib.state.initialize;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.petra.lib.block.BlockId;
import com.petra.lib.context.StateError;
import com.petra.lib.context.ExecutionContext;
import com.petra.lib.manager.ExecutionStatus;
import com.petra.lib.block.models.BlockModel;
import com.petra.lib.state.State;
import com.petra.lib.state.StateHandler;
import com.petra.lib.state.StateManager;
import com.petra.lib.state.repo.JobRepository;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;

import java.util.Optional;

/**
 * Manage the start of block execution. Initialize execution context and check previous executions.
 */
@Log4j2
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class Initializer implements StateHandler {

    /**
     * Block id
     */
    BlockId blockId;
    String blockName;
    StateManager stateManager;
    JobRepository jobRepository;

    public Initializer(BlockModel blockModel, StateManager stateManager, JobRepository jobRepository) {
        this.blockId = new BlockId(blockModel.getId(), blockModel.getVersion());
        this.blockName = blockModel.getName();
        this.stateManager = stateManager;
        this.jobRepository = jobRepository;
    }

    @Override
    public void execute(ExecutionContext context) throws JsonProcessingException {
        //проверить была ли раьше вызвана данная обработка
        Optional<ExecutionStatus> prevStatus = jobRepository.isExecutedBefore(context.getScenarioId(), blockId);

        if (prevStatus.isPresent()) {
            ExecutionStatus curStatus = prevStatus.get();

            switch (curStatus) {
                //стандартное прожолжение исплнения  активности
                case STARTED:
                    jobRepository.setExecutingStatus(ExecutionStatus.EXECUTING);
                    log.debug("Job starts executed {} {}", blockName, context.toString());
                    stateManager.executedState(context, State.INITIALIZING);
                    break;

                //активность либо была исполнена ранее либо исполняется сейчас.
                //закончить выполнение активности путем высавление ошибки
                case EXECUTED:
                case EXECUTING:
                    log.debug("Job already executed {} {}", blockName, context.toString());
                    stateManager.executedState(context, State.INITIALIZING, StateError.ALREADY_EXECUTING);
                    break;

                case ERROR:
                    String error = jobRepository.getErrorMessage(context.getScenarioId(), blockId);
                    log.debug("Job already end with error {} {} : {}", blockName, context.toString(), error);
                    stateManager.executedState(context, State.INITIALIZING, StateError.INNER_ERROR);
                    break;
            }
        }
        throw new IllegalStateException("Execution status not found");
    }

    @Override
    public State getManagerState() {
        return State.INITIALIZING;
    }

    @Override
    public void start() {

    }
}
