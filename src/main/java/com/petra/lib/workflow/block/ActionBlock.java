package com.petra.lib.workflow.block;

import com.petra.lib.action.BlockState;
import com.petra.lib.remote.dto.SourceResponseDto;
import com.petra.lib.remote.enums.BlockExecutionStatus;
import com.petra.lib.remote.enums.BlockRequestResult;
import com.petra.lib.variable.value.ValuesContainer;
import com.petra.lib.variable.value.ValuesContainerFactory;
import com.petra.lib.workflow.block.executor.BlockExecutor;
import com.petra.lib.workflow.block.source.SourceHandler;
import com.petra.lib.workflow.context.WorkflowActionContext;
import com.petra.lib.workflow.context.WorkflowContext;
import com.petra.lib.workflow.enums.WorkflowActionState;
import com.petra.lib.workflow.repo.WorkflowActionRepo;
import com.petra.lib.workflow.repo.WorkflowRepo;
import com.petra.lib.workflow.signal.Signal;
import com.petra.lib.workflow.signal.SignalManager;
import com.petra.lib.workflow.subscribe.Listener;
import com.petra.lib.workflow.subscribe.SubscribeManager;
import com.petra.lib.workflow.subscribe.WorkflowSignal;
import com.petra.lib.workflow.subscribe.WorkflowSignalModel;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

/**
 * Класс отвечает за запуск активности из воркфлоу
 */
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class ActionBlock implements Listener {

    Long actionId;
    WorkflowActionRepo workflowActionRepo;
    SourceHandler sourceHandler;
    BlockExecutor blockExecutor;
    Set<Long> listeningSignals;
    SubscribeManager subscribeManager;
    SignalManager signalManager;
    WorkflowRepo workflowRepo;

    boolean lastBlock;
    boolean isContextBlock;

    @Override
    public void handle(WorkflowSignal workflowSignal, WorkflowSignalModel workflowSignalModel) {
        switch (workflowSignal) {
            case EXECUTE_SIGNAL:
                executeSignal(workflowSignalModel.getExecutedSignal(),
                        workflowSignalModel.getScenarioId(),
                        workflowSignalModel.getWorkflowId());
                break;
            case EXECUTION_BLOCK_COMPLETE_INCOME_MESSAGE:
                answerState(workflowSignalModel.getScenarioId(), workflowSignalModel.getWorkflowId(),
                        ValuesContainerFactory.fromJson(workflowSignalModel.getBlockResponseDto().getBlockVariables()),
                        workflowSignalModel.getBlockResponseDto().getNextSignalId(),
                        workflowSignalModel.getBlockResponseDto().getBlockExecutionStatus());
                break;
            case SOURCE_INCOME_MESSAGE:
                setSourceAnswer(workflowSignalModel.getScenarioId(), workflowSignalModel.getWorkflowId(),
                        workflowSignalModel.getSourceResponseDto());
                break;
        }
    }

    private void executeSignal(Signal signal, UUID scenarioId, Long workflowId) {
        if (!listeningSignals.contains(signal.getSignalId())) {
            return;
        }

        Optional<WorkflowContext> workflowContext = workflowRepo.findContext(scenarioId, workflowId);
        if (workflowContext.get().getWorkflowState() == BlockState.ERROR) {
            return;
        }

        WorkflowActionContext workflowActionContext = new WorkflowActionContext(
                scenarioId,
                workflowId,
                actionId,
                signal.getSignalVariables(),
                signal.getSignalId(),
                Collections.emptySet()
        );
        workflowActionContext.setWorkflowState(WorkflowActionState.LOAD_VARIABLES);
        boolean isContextSaved = workflowActionRepo.createContext(workflowActionContext);


        if (!isContextSaved) {
            Optional<WorkflowActionContext> oldWorkflowContextOpt
                    = workflowActionRepo.findContext(scenarioId, workflowId, actionId);
            WorkflowActionContext oldWorkflowContext = oldWorkflowContextOpt.get();
            if (oldWorkflowContext.getWorkflowActionState() == WorkflowActionState.LOAD_VARIABLES) {
                loadVariablesState(oldWorkflowContext);
            } else if (oldWorkflowContext.getWorkflowActionState() == WorkflowActionState.EXECUTING) {
                executingState(oldWorkflowContext);
            } else if (oldWorkflowContext.getWorkflowActionState() == WorkflowActionState.COMPLETE) {
                answerState(scenarioId, workflowId, oldWorkflowContext.getBlockVariables(),
                        oldWorkflowContext.getNextSignalId(), BlockExecutionStatus.EXECUTED);
            }
            return;
        }
        loadVariablesState(workflowActionContext);
    }

    private void sendNextSignal(WorkflowActionContext oldWorkflowContext, UUID scenarioId, Long workflowId) {
        Signal nextSignal = signalManager.createParsedSignal(oldWorkflowContext.getNextSignalId(),
                oldWorkflowContext.getBlockVariables());
        subscribeManager.sendSignal(WorkflowSignal.EXECUTE_SIGNAL, WorkflowSignalModel.builder()
                .executedSignal(nextSignal)
                .scenarioId(scenarioId)
                .workflowId(workflowId)
                .build());
    }

    private void loadVariablesState(WorkflowActionContext workflowActionContext) {
        boolean isSourcesLoaded = sourceHandler.startValuesFilling(workflowActionContext);
        if (isSourcesLoaded) {
            executingState(workflowActionContext);
        }
    }

    private void executingState(WorkflowActionContext workflowActionContext) {
        BlockRequestResult result = blockExecutor.startExecuting(workflowActionContext);
        switch (result) {
            case OK:
                break;
            case ERROR:
                errorState(workflowActionContext);
                break;
            case REPEAT:
                Optional<WorkflowActionContext> oldWorkflowContext
                        = workflowActionRepo.findContext(workflowActionContext.getScenarioId(),
                        workflowActionContext.getWorkflowId(), actionId);
                if (oldWorkflowContext.get().getWorkflowActionState() != WorkflowActionState.EXECUTING) {
                    sendNextSignal(oldWorkflowContext.get(), workflowActionContext.getScenarioId(),
                            workflowActionContext.getWorkflowId());
                }
                break;

        }
    }


    private void answerState(UUID scenarioId, Long workflowId, ValuesContainer blockValues,
                             Long nextSignalId, BlockExecutionStatus blockExecutionStatus) {

        Optional<WorkflowActionContext> workflowContextOpt
                = workflowActionRepo.findContext(scenarioId, workflowId, actionId);
        WorkflowActionContext workflowContext = workflowContextOpt.get();
        if (blockExecutionStatus == BlockExecutionStatus.ERROR) {
            errorState(workflowContext);
            return;
        }
        workflowContext.setBlockVariables(blockValues);
        workflowContext.setNextSignalId(nextSignalId);
        workflowActionRepo.updateStateToComplete(
                workflowContext.getBlockVariables(),
                workflowContext.getNextSignalId(),
                workflowContext
        );
        if (lastBlock) {
            subscribeManager.sendSignal(WorkflowSignal.LAST_SIGNAL_EXECUTED, WorkflowSignalModel.builder()
                    .workflowActionContext(workflowContext)
                    .workflowId(workflowId)
                    .scenarioId(scenarioId)
                    .isContextSignal(isContextBlock)
                    .build());
        } else {
            sendNextSignal(workflowContext, scenarioId, workflowId);
        }
    }


    private void errorState(WorkflowActionContext workflowActionContext) {
        workflowActionRepo.updateStateToError(workflowActionContext);
        subscribeManager.sendSignal(WorkflowSignal.EXECUTION_BLOCK_ERROR, WorkflowSignalModel.builder()
                .scenarioId(workflowActionContext.getScenarioId())
                .workflowId(workflowActionContext.getWorkflowId())
                .blockId(actionId)
                .build());
    }

    private void setSourceAnswer(UUID scenarioId, Long workflowId, SourceResponseDto sourceResponseDto) {
        Optional<WorkflowActionContext> workflowContextOpt
                = workflowActionRepo.findContext(scenarioId, workflowId, actionId);
        WorkflowActionContext workflowContext = workflowContextOpt.get();
        boolean isSourcesLoaded = sourceHandler.setSourceAnswer(workflowContext, sourceResponseDto);
        if (isSourcesLoaded) {
            executingState(workflowContext);
        }
    }
}
