package com.petra.lib.workflow.block.source;

import com.petra.lib.remote.dto.SourceRequestDto;
import com.petra.lib.remote.enums.BlockRequestResult;
import com.petra.lib.remote.request.source.SourceRequest;
import com.petra.lib.variable.pure.PureVariableList;
import com.petra.lib.variable.value.ProcessValue;
import com.petra.lib.variable.value.ValuesContainer;
import com.petra.lib.variable.value.ValuesContainerFactory;
import com.petra.lib.workflow.context.WorkflowActionContext;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.Collection;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
class SourceLoader {
    SourceRequest sourceRequest;
    @Getter
    Long requestingSourceId;
    PureVariableList sourcePureVariableList;
    String requestingSourceServiceName;

    public BlockRequestResult load(WorkflowActionContext workflowActionContext) {
        Collection<ProcessValue> sourceContainer = sourcePureVariableList.parseVariables(workflowActionContext.getBlockVariables().getValues());
        ValuesContainer valuesContainer = ValuesContainerFactory.createValuesContainer(sourceContainer);
        SourceRequestDto sourceRequestDto = new SourceRequestDto(
                workflowActionContext.getScenarioId(),
                requestingSourceId,
                ValuesContainerFactory.toJson(valuesContainer),
                workflowActionContext.getWorkflowId(),
                requestingSourceServiceName,
                workflowActionContext.getExecBlock());
        return sourceRequest.send(sourceRequestDto, requestingSourceServiceName);
    }
}
