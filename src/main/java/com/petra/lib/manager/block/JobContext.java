package com.petra.lib.manager.block;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Job context during execution
 */
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@ToString
public class JobContext {

    Map<Long, ProcessVariableDto> processVariableMap = new HashMap<>();

    @Getter
    UUID scenarioId;

    @Getter
    Long requestSourceId;

    JobContext(UUID scenarioId, Collection<ProcessVariableDto> processVariableDtoList, Long requestSourceId){
        this.scenarioId = scenarioId;
        this.requestSourceId = requestSourceId;
        processVariableDtoList.forEach(var -> processVariableMap.put(var.getId(), var));
    }

    public void setVariable(ProcessVariableDto processVariableDto) {
        processVariableMap.put(processVariableDto.getId(), processVariableDto);
    }

    public ProcessVariableDto getVariableById(Long id) {
        return processVariableMap.get(id);
    }


    public String getVariablesJson() throws JsonProcessingException {
        Collection<ProcessVariableDto> processVariableDtos = processVariableMap.values();
        return new ObjectMapper().writeValueAsString(processVariableDtos);
    }

    public Collection<ProcessVariableDto> getProcessVariables(){
        return processVariableMap.values();
    }
}
