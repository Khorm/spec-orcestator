package com.petra.lib.workflow;

import com.petra.lib.action.BlockState;
import com.petra.lib.remote.dto.BlockRequestDto;
import com.petra.lib.remote.dto.BlockResponseDto;
import com.petra.lib.remote.dto.SourceResponseDto;
import com.petra.lib.remote.enums.BlockExecutionStatus;
import com.petra.lib.remote.enums.BlockRequestResult;
import com.petra.lib.remote.enums.BlockResponseResult;
import com.petra.lib.variable.value.ValuesContainer;
import com.petra.lib.variable.value.ValuesContainerFactory;
import com.petra.lib.workflow.context.WorkflowActionContext;
import com.petra.lib.workflow.context.WorkflowContext;
import com.petra.lib.workflow.enums.WorkflowActionState;
import com.petra.lib.workflow.repo.WorkflowActionRepo;
import com.petra.lib.workflow.repo.WorkflowRepo;
import com.petra.lib.workflow.response.WorkflowResponseExecutor;
import com.petra.lib.workflow.signal.Signal;
import com.petra.lib.workflow.signal.SignalManager;
import com.petra.lib.workflow.subscribe.Listener;
import com.petra.lib.workflow.subscribe.SubscribeManager;
import com.petra.lib.workflow.subscribe.WorkflowSignal;
import com.petra.lib.workflow.subscribe.WorkflowSignalModel;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Обрабатывает общение к воркфлоу
 */
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class Workflow implements Listener {

    Long workflowId;
    WorkflowRepo workflowRepo;
    SignalManager signalManager;
    WorkflowResponseExecutor workflowResponseExecutor;
    SubscribeManager subscribeManager;
    WorkflowActionRepo workflowActionRepo;
    Collection<Long> allContainerBlockIds;


    /**
     * Запуск работы воркфлоу
     */
    public void start(){
        Collection<WorkflowContext> notEndedContexts = workflowRepo.findAllNotEndedContextsByWorkflowId(workflowId);

        for (WorkflowContext workflowContext : notEndedContexts){
            if (workflowContext.getWorkflowState() == BlockState.EXECUTING) {
                sendInitSignal(workflowContext);
            }else if (workflowContext.getWorkflowState() == BlockState.EXECUTED){
                workflowResponseExecutor.answer(workflowContext, BlockExecutionStatus.EXECUTED);
            }
        }
    }

    /**
     * Выполнить воркфлоу
     * @param blockRequestDto
     * @return
     */
    public BlockRequestResult execute(BlockRequestDto blockRequestDto) {
        ValuesContainer valuesContainer = ValuesContainerFactory.fromJson(blockRequestDto.getBlockVariables());
        WorkflowContext workflowContext = new WorkflowContext(
                blockRequestDto.getScenarioId(),
                blockRequestDto.getProducerId(),
                workflowId,
                blockRequestDto.getSignalId(),
                valuesContainer,
                blockRequestDto.getRequestWorkflowServiceName()
        );
        workflowContext.setWorkflowState(BlockState.EXECUTING);

        boolean isContextFind = workflowRepo.createContext(workflowContext);
        if (isContextFind) return BlockRequestResult.REPEAT;

        sendInitSignal(workflowContext);
        return BlockRequestResult.OK;
    }

    /**
     * Обработать ответ от соурса
     * @param sourceResponseDto
     * @return
     */
    public BlockResponseResult sourceAnswer(SourceResponseDto sourceResponseDto) {
        subscribeManager.sendSignal(WorkflowSignal.SOURCE_INCOME_MESSAGE, WorkflowSignalModel.builder()
                .sourceResponseDto(sourceResponseDto)
                .workflowId(workflowId)
                .scenarioId(sourceResponseDto.getScenarioId())
                .build());
        return BlockResponseResult.OK;

    }

    /**
     * Обработать ответ от блока
     * @param blockResponseDto
     * @return
     */
    public BlockResponseResult blockExecuted(BlockResponseDto blockResponseDto) {
        subscribeManager.sendSignal(WorkflowSignal.EXECUTION_BLOCK_COMPLETE_INCOME_MESSAGE, WorkflowSignalModel.builder()
                .blockResponseDto(blockResponseDto)
                .workflowId(workflowId)
                .scenarioId(blockResponseDto.getScenarioId())
                .build());
        return BlockResponseResult.OK;
    }

    /**
     * Обработать подписку
     * @param workflowSignal
     * @param workflowSignalModel
     */
    @Override
    public void handle(WorkflowSignal workflowSignal, WorkflowSignalModel workflowSignalModel) {
        switch(workflowSignal){
            case EXECUTION_BLOCK_ERROR:
                executeErrorState(workflowSignalModel.getScenarioId());
                break;

            case LAST_SIGNAL_EXECUTED:
                if (workflowSignalModel.isContextSignal()){
                    workflowRepo.updateValues(workflowSignalModel.getScenarioId(), workflowId,
                            workflowSignalModel.getWorkflowActionContext().getBlockVariables());
                }
                if (!checkBlocksReady(workflowSignalModel.getScenarioId(), workflowId)) return;
                executeWorkflow(workflowSignalModel.getScenarioId());
                break;

        }
    }

    private void executeWorkflow(UUID scenarioId){
        Optional<WorkflowContext> workflowContextOpt = workflowRepo.findContext(scenarioId, workflowId);
        WorkflowContext workflowContext = workflowContextOpt.get();
        workflowContext.setWorkflowState(BlockState.EXECUTED);
        boolean isStateAccepted = workflowRepo.updateStateToExecuted(workflowContext);
        if (!isStateAccepted) return;

        workflowResponseExecutor.answer(workflowContext, BlockExecutionStatus.EXECUTED);
    }

    private void executeErrorState(UUID scenarioId){
        Optional<WorkflowContext> workflowContextOpt = workflowRepo.findContext(scenarioId, workflowId);
        WorkflowContext workflowContext = workflowContextOpt.get();
        workflowContext.setWorkflowState(BlockState.ERROR);
        boolean isStateAccepted = workflowRepo.updateStateToError(workflowContext,
                BlockState.ERROR);
        if (!isStateAccepted) return;

        workflowResponseExecutor.answer(workflowContext, BlockExecutionStatus.ERROR);
    }

    /**
     * Проверить все ли блоки внутри сценария были исполнены
     * @param scenarioId
     * @param workflowId
     * @return
     */
    private boolean checkBlocksReady(UUID scenarioId, Long workflowId) {
        Set<Long> executedBlocks = workflowActionRepo.getScenarioContexts(scenarioId, workflowId)
                .stream()
                .flatMap((Function<WorkflowActionContext, Stream<Long>>) workflowActionContext -> {
                    if (workflowActionContext.getWorkflowActionState() == WorkflowActionState.COMPLETE) {
                        return Stream.of(workflowActionContext.getExecBlock());
                    }
                    return null;
                })
                .collect(Collectors.toSet());
        for (Long blockId : allContainerBlockIds) {
            if (!executedBlocks.contains(blockId)) {
                return false;
            }
        }
        return true;
    }

    private void sendInitSignal(WorkflowContext workflowContext){
        Signal workflowInitSignal = signalManager.createSimpleSignal(workflowContext.getProducerId(),
                workflowContext.getSignalVariables());
        subscribeManager.sendSignal(WorkflowSignal.EXECUTE_SIGNAL, WorkflowSignalModel.builder()
                .executedSignal(workflowInitSignal)
                .workflowId(workflowId)
                .scenarioId(workflowContext.getScenarioId())
                .build());
    }
}
