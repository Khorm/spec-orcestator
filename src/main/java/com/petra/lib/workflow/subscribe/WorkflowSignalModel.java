package com.petra.lib.workflow.subscribe;

import com.petra.lib.remote.dto.BlockResponseDto;
import com.petra.lib.remote.dto.SourceResponseDto;
import com.petra.lib.workflow.context.WorkflowActionContext;
import com.petra.lib.workflow.context.WorkflowContext;
import com.petra.lib.workflow.signal.Signal;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Builder
public class WorkflowSignalModel {
    WorkflowContext workflowContext;
    WorkflowActionContext workflowActionContext;
    Signal executedSignal;
    UUID scenarioId;
    Long workflowId;
    Long blockId;
    BlockResponseDto blockResponseDto;
    SourceResponseDto sourceResponseDto;
    boolean isContextSignal;
}
