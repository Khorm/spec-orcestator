package com.petra.lib.workflow;

import com.petra.lib.action.BlockState;
import com.petra.lib.query.ThreadQuery;
import com.petra.lib.remote.dto.BlockRequestDto;
import com.petra.lib.remote.dto.BlockResponseDto;
import com.petra.lib.remote.enums.BlockRequestResult;
import com.petra.lib.remote.enums.BlockRequestStatus;
import com.petra.lib.remote.enums.BlockResponseResult;
import com.petra.lib.workflow.block.BlockManager;
import com.petra.lib.workflow.context.WorkflowContext;
import com.petra.lib.workflow.repo.WorkflowRepo;
import com.petra.lib.workflow.response.WorkflowResponseExecutor;
import com.petra.lib.workflow.signal.Signal;
import com.petra.lib.workflow.signal.SignalManager;

import java.util.Optional;

/**
 * Обрабатывает общение к воркфлоу
 */
public class Workflow {

    Long workflowId;
    WorkflowRepo workflowRepo;
    BlockManager blockManager;
    SignalManager signalManager;

    ThreadQuery threadQuery;
    WorkflowResponseExecutor workflowResponseExecutor;


    public BlockRequestResult execute(BlockRequestDto blockRequestDto) {
        WorkflowContext workflowContext = new WorkflowContext(
                blockRequestDto.getScenarioId(),
                blockRequestDto.getProducerId(),
                workflowId,
                blockRequestDto.getSignalId(),
                blockRequestDto.getBlockVariables(),
                blockRequestDto.getRequestWorkflowServiceName()
        );
        workflowContext.setWorkflowState(BlockState.EXECUTING);

        boolean isContextFind = workflowRepo.createContext(workflowContext);
        if (isContextFind) return BlockRequestResult.REPEAT;
        threadQuery.popInQueue(() -> {
            //создать вызываемый сигнал
            Signal workflowInitSignal = signalManager.createSimpleSignal(blockRequestDto.getProducerId(),
                    blockRequestDto.getBlockVariables());
            blockManager.execute(workflowContext, workflowInitSignal);
        });
        return BlockRequestResult.OK;
    }

    /**
     * Был выполнен внутренний блок
     *
     * @param blockResponseDto
     */
    public BlockResponseResult blockExecuted(BlockResponseDto blockResponseDto) {
        boolean isHandlingAccept = blockManager.blockExecuted(blockResponseDto, workflowId);
        if (!isHandlingAccept) return BlockResponseResult.ERROR;

        threadQuery.popInQueue(() -> {

            Optional<WorkflowContext> contextOptional =
                    workflowRepo.findContext(blockResponseDto.getScenarioId(), workflowId);
            if (contextOptional.isEmpty()) return;

            WorkflowContext context = contextOptional.get();
            if (context.getWorkflowState() != BlockState.EXECUTING) return;

            boolean isWorkflowExecuted = blockManager.checkBlocksReady(blockResponseDto.getScenarioId(), workflowId);
            if (isWorkflowExecuted) {
                context.setWorkflowState(BlockState.EXECUTED);
                context.setResponseVariables(blockResponseDto.getBlockVariables());
                boolean saveResult = workflowRepo.updateContext(context);
                if (saveResult) return;
//                отправлять сообщение о готовности
                workflowResponseExecutor.answer(context, BlockRequestStatus.EXECUTED);
            } else {
                Signal signal = signalManager.createParsedSignal(blockResponseDto.getExecutedBlockId(),
                        blockResponseDto.getCallingSignalId(),
                        blockResponseDto.getBlockVariables());
                blockManager.execute(contextOptional.get(), signal);
            }
        });
        return BlockResponseResult.OK;
    }


}
