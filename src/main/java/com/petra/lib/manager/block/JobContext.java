package com.petra.lib.manager.block;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.petra.lib.signal.model.SignalTransferModel;
import com.petra.lib.variable.mapper.VariableMapper;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Job context during execution
 */
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class JobContext {

    Map<Long, ProcessVariableDto> processVariableMap = new HashMap<>();
    UUID scenarioId;

    public JobContext(UUID scenarioId, Collection<ProcessVariableDto> processVariableDtoList){
        this.scenarioId = scenarioId;
        processVariableDtoList.forEach(var -> processVariableMap.put(var.getId(), var));
    }
//    /**
//     * Request signal model
//     */
//    @Getter
//    SignalTransferModel requestSignalTransferModel;

//    public JobContext(SignalTransferModel requestSignalTransferModel) {
//        this.requestSignalTransferModel = requestSignalTransferModel;
//    }

    public void setVariable(ProcessVariableDto processVariableDto) {
        processVariableMap.put(processVariableDto.getId(), processVariableDto);
    }

    public ProcessVariableDto getVariableById(Long id) {
        return processVariableMap.get(id);
    }

//    public Collection<ProcessVariableDto> getSignalVariables() {
//        return requestSignalTransferModel.getSignalVariables();
//    }

    public UUID getScenarioId() {
        return scenarioId;
    }


    public String getVariablesJson() throws JsonProcessingException {
        Collection<ProcessVariableDto> processVariableDtos = processVariableMap.values();
        return new ObjectMapper().writeValueAsString(processVariableDtos);
    }

    public Collection<ProcessVariableDto> getProcessVariables(){
        return processVariableMap.values();
    }
}
