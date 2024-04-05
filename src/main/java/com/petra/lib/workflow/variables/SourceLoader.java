package com.petra.lib.workflow.variables;

import com.petra.lib.remote.dto.SourceRequestDto;
import com.petra.lib.remote.dto.SourceResponseDto;
import com.petra.lib.remote.request.source.SourceRequest;
import com.petra.lib.variable.mapper.VariableMapper;
import com.petra.lib.variable.value.VariablesContainer;
import com.petra.lib.workflow.context.WorkflowActionContext;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
class SourceLoader {
    SourceRequest sourceRequest;
    Long loadingSourceId;
    VariableMapper variableMapper;

    public SourceResponseDto load(WorkflowActionContext workflowActionContext){
        VariablesContainer sourceContainer = variableMapper.map(workflowActionContext.getBlockVariables());
        SourceRequestDto sourceRequestDto = new SourceRequestDto(workflowActionContext.getScenarioId(),
                loadingSourceId, sourceContainer);
        return sourceRequest.send(sourceRequestDto);
    }
}
