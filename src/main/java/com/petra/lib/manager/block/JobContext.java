package com.petra.lib.manager.block;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.petra.lib.signal.SignalId;
import com.petra.lib.signal.dto.RequestDto;
import com.petra.lib.variable.base.VariableList;
import com.petra.lib.worker.handler.UserContext;
import com.petra.lib.worker.handler.impl.UserContextImpl;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

import javax.persistence.EntityManager;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Job context during execution
 */
@FieldDefaults(level = AccessLevel.PRIVATE)
@ToString
public class JobContext {

    final Map<Long, ProcessVariableDto> processVariableMap = new HashMap<>();

    final RequestDto initRequestDto;
    final Map<Long, ProcessVariableDto> initRequestVariablesMap;

    @Getter
    final SignalId requestSignalId;

    @Getter
    final BlockId blockId;

    final VariableList variableList;

    @Getter
    boolean isAlreadyDone = false;

    JobContext(RequestDto initRequestDto, SignalId requestSignalId, VariableList variableList, BlockId blockId) {
        this.initRequestDto = initRequestDto;
        initRequestVariablesMap = initRequestDto.getSignalVariables().stream()
                .collect(Collectors.toMap(ProcessVariableDto::getId, Function.identity()));

        this.requestSignalId = requestSignalId;
        this.blockId = blockId;
        this.variableList = variableList;
    }

    public void setVariable(ProcessVariableDto processVariableDto) {
        processVariableMap.put(processVariableDto.getId(), processVariableDto);
    }

    public ProcessVariableDto getVariableById(Long id) {
        return processVariableMap.get(id);
    }

    public Collection<ProcessVariableDto> getProcessVariables() {
        return processVariableMap.values();
    }


    public String getVariablesJson() throws JsonProcessingException {
        Collection<ProcessVariableDto> processVariableDtos = processVariableMap.values();
        return new ObjectMapper().writeValueAsString(processVariableDtos);
    }

    public ProcessVariableDto getProcessVariable(Long processVariableId) {
        return processVariableMap.get(processVariableId);
    }

    public Collection<ProcessVariableDto> getInitSignalVariables() {
        return initRequestDto.getSignalVariables();
    }

    public ProcessVariableDto getInitSignalVariable(Long initSignalVariableId) {
        return initRequestVariablesMap.get(initSignalVariableId);
    }

    public UserContext getUserContext(EntityManager entityManager) {
        return new UserContextImpl(this, entityManager, variableList);
    }

    public BlockId getRequestBlockId() {
        return initRequestDto.getRequestBlockId();
    }

    public UUID getScenarioId() {
        return initRequestDto.getScenarioId();
    }

    public void setAlreadyDone(){
        this.isAlreadyDone = true;
    }


}
