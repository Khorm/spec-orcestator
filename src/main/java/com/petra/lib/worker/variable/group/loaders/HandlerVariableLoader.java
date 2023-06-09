package com.petra.lib.worker.variable.group.loaders;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.petra.lib.PetraException;
import com.petra.lib.manager.block.JobContext;
import com.petra.lib.manager.block.ProcessVariableDto;
import com.petra.lib.worker.variable.group.handler.VariableContext;
import com.petra.lib.worker.variable.group.handler.VariableHandler;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.Collection;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
class HandlerVariableLoader implements VariableLoader {

    Long currentBlockVariableId;
    VariableHandler variableHandler;
    ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void load(Collection<ProcessVariableDto> sourceVariables, JobContext jobContext){
        VariableContext variableContext = jobContext.getUserContext(null);
        Object result = variableHandler.map(variableContext);
        ProcessVariableDto processVariableDto = null;
        try {
            processVariableDto = new ProcessVariableDto(currentBlockVariableId, objectMapper.writeValueAsString(result));
        } catch (JsonProcessingException e) {
            throw new PetraException("Error in mapping variables", e);
        }
        jobContext.setVariable(processVariableDto);
    }
}
