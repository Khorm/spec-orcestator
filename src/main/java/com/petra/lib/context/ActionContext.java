package com.petra.lib.context;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.petra.lib.block.BlockId;
import com.petra.lib.block.ProcessValue;
import com.petra.lib.signal.dto.RequestDto;
import com.petra.lib.variable.base.StatelessVariableList;
import lombok.AccessLevel;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

import javax.persistence.EntityManager;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Job context during execution
 */
@FieldDefaults(level = AccessLevel.PRIVATE)
@ToString
class ActionContext implements ExecutionContext{

    final Map<Long, ProcessValue> processValueMap = new HashMap<>();

    final RequestDto request;
//    final Map<Long, ProcessVariableDto> initRequestVariablesMap;

//    @Getter
//    final SignalId requestSignalId;

    final BlockId blockId;

    final StatelessVariableList statelessVariableList;

//    @Getter
//    boolean isAlreadyDone = false;

    /**
     *
     * @param initRequestDto - request from signal that initializes execution
     * @param requestSignalId - id from initializes signal
     * @param statelessVariableList - list
     * @param blockId
     */
    ActionContext(RequestDto request, /*SignalId requestSignalId,*/ StatelessVariableList statelessVariableList, BlockId blockId) {
        this.request = request;
//        initRequestVariablesMap = initRequestDto.getSignalVariables().stream()
//                .collect(Collectors.toMap(ProcessVariableDto::getId, Function.identity()));

//        this.requestSignalId = requestSignalId;
        this.blockId = blockId;
        this.statelessVariableList = statelessVariableList;
    }

    public void setVariable(ProcessValue processValue) {
        processVariableMap.put(processValue.getId(), processValue);
    }

    public ProcessValue getVariableById(Long id) {
        return processVariableMap.get(id);
    }

    public Collection<ProcessValue> getProcessVariables() {
        return processVariableMap.values();
    }


    public String getVariablesJson() throws JsonProcessingException {
        Collection<ProcessValue> processValues = processVariableMap.values();
        return new ObjectMapper().writeValueAsString(processValues);
    }

    public ProcessValue getProcessVariable(Long processVariableId) {
        return processVariableMap.get(processVariableId);
    }

    public Collection<ProcessValue> getInitSignalVariables() {
        return initRequestDto.getSignalVariables();
    }

//    public ProcessVariableDto getInitSignalVariable(Long initSignalVariableId) {
//        return initRequestVariablesMap.get(initSignalVariableId);
//    }

    public UserContext getUserContext(EntityManager entityManager) {
        return new UserContextImpl(this, entityManager, statelessVariableList);
    }

    public BlockId getRequestBlockId() {
        return initRequestDto.getRequestBlockId();
    }

    public UUID getScenarioId() {
        return initRequestDto.getScenarioId();
    }

    @Override
    public synchronized void setValues(Collection<ProcessValue> values) {
        for (ProcessValue processValue : values){
            processValueMap.put(processValue.getVariableId(), processValue);
        }
    }

//    public void setAlreadyDone(){
//        this.isAlreadyDone = true;
//    }


}
