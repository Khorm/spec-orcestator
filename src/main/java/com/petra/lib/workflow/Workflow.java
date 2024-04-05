package com.petra.lib.workflow;

import com.petra.lib.remote.dto.BlockRequestDto;
import com.petra.lib.remote.dto.BlockResponseDto;
import com.petra.lib.remote.enums.BlockRequestResult;
import com.petra.lib.workflow.block.BlockManager;
import com.petra.lib.workflow.context.WorkflowContext;
import com.petra.lib.workflow.enums.WorkflowState;
import com.petra.lib.workflow.repo.WorkflowRepo;
import com.petra.lib.workflow.signal.Signal;
import com.petra.lib.workflow.signal.SignalManager;

import java.util.Optional;

public class Workflow {

    Long workflowId;
    WorkflowRepo workflowRepo;
    BlockManager blockManager;

    SignalManager signalManager;

    public BlockRequestResult execute(BlockRequestDto blockRequestDto){
        WorkflowContext workflowContext = new WorkflowContext(
                blockRequestDto.getScenarioId(),
                workflowId,
                blockRequestDto.getProducerId(),
                blockRequestDto.getSignalId(),
                blockRequestDto.getBlockVariables(),
                blockRequestDto.getRequestWorkflowServiceName()
        );
        workflowContext.setWorkflowState(WorkflowState.EXECUTING);

        boolean isContextFind = workflowRepo.save(workflowContext);

        if (isContextFind) return BlockRequestResult.REPEAT;

        Signal workflowInitSignal = signalManager.createSimpleSignal(blockRequestDto.getProducerId(),
                blockRequestDto.getBlockVariables());
        blockManager.execute(workflowContext, workflowInitSignal);
        return BlockRequestResult.OK;
    }

    /**
     * Был выполнен внутренний блок
     * @param blockResponseDto
     */
    public void blockExecuted(BlockResponseDto blockResponseDto){
        Optional<WorkflowContext> contextOptional =
                workflowRepo.findContext(blockResponseDto.getScenarioId(), workflowId);
        if (contextOptional.isPresent()){
            Signal signal = signalManager.createParsedSignal(blockResponseDto.getBlockId(), blockResponseDto.getBlockVariables());
            blockManager.execute(contextOptional.get(), signal);
        }
    }
}
