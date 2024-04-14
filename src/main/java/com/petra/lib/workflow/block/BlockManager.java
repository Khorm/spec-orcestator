package com.petra.lib.workflow.block;

import com.petra.lib.remote.dto.BlockResponseDto;
import com.petra.lib.workflow.context.WorkflowActionContext;
import com.petra.lib.workflow.context.WorkflowContext;
import com.petra.lib.workflow.enums.WorkflowActionState;
import com.petra.lib.workflow.repo.WorkflowActionRepo;
import com.petra.lib.workflow.signal.Signal;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Управляет запуском следующих блоков
 */
public class BlockManager {

    Map<Long, Collection<ExecutionBlock>> blocksBySignals = new HashMap<>();
    WorkflowActionRepo workflowActionRepo;

    List<Long> allBlockIds;

    /**
     * Обрабатывает входящий сигнал активностей
     *
     * @param workflowContext
     * @param signal
     */
    public void execute(WorkflowContext workflowContext, Signal signal) {
        for (ExecutionBlock executionBlock : blocksBySignals.get(workflowContext.getSignalId())) {
            WorkflowActionContext workflowActionContext = new WorkflowActionContext(
                    workflowContext.getScenarioId(),
                    workflowContext.getConsumerId(),
                    executionBlock.getId(),
                    signal.getSignalId(),
                    signal.getSignalVariables()
            );
            workflowActionContext.setWorkflowState(WorkflowActionState.LOAD_VARIABLES);

            boolean isContextSaved = workflowActionRepo.saveContext(workflowActionContext);

            //Если контекст уже существует - пропустить задачку
            if (!isContextSaved) return;
            executionBlock.start(workflowActionContext);
        }
    }

    /**
     *
     * @param blockResponseDto
     * @param workflowId
     * @return
     * true - продолжить обработку исполнения активности
     * false - остановить обработку активности
     */
    public boolean blockExecuted(BlockResponseDto blockResponseDto, Long workflowId) {
        Optional<WorkflowActionContext> workflowActionContextOptional =
                workflowActionRepo.findContext(blockResponseDto.getScenarioId(),
                        workflowId, blockResponseDto.getExecutedBlockId());
        if (workflowActionContextOptional.isEmpty()) {
            throw new IllegalArgumentException("Block context not found");
        }
        WorkflowActionContext workflowActionContext = workflowActionContextOptional.get();
        workflowActionContext.setWorkflowState(WorkflowActionState.COMPLETE);
        workflowActionContext.setBlockVariables(blockResponseDto.getBlockVariables());
        workflowActionContext.setCallingSignalId(blockResponseDto.getCallingSignalId());
        return workflowActionRepo.updateContext(workflowActionContext);
    }


    public boolean checkBlocksReady(UUID scenarioId, Long workflowId){
        Set<Long> executedBlocks = workflowActionRepo.getScenarioContexts(scenarioId, workflowId)
                .stream()
                .flatMap((Function<WorkflowActionContext, Stream<Long>>) workflowActionContext -> {
                    if (workflowActionContext.getWorkflowActionState() == WorkflowActionState.COMPLETE){
                        return Stream.of(workflowActionContext.getExecBlock());
                    }
                    return null;
                })
                .collect(Collectors.toSet());
        for (Long blockId: allBlockIds){
            if (!executedBlocks.contains(blockId)){
                return false;
            }
        }
        return true;
    }
}
