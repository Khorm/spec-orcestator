package com.petra.lib.workflow.context;

import com.petra.lib.variable.value.ValuesContainer;
import com.petra.lib.workflow.enums.WorkflowActionState;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.Set;
import java.util.UUID;
@Getter
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class WorkflowActionContext {

    /**
     * айди сцаенария
     */
    final UUID scenarioId;

    /**
     * Айди воркфлоу которая обрабатывает этот блок
     */
    final Long workflowId;

    /**
     * Исполняемый блок
     */
    final Long execBlock;

    /**
     * Статус блока
     */
    WorkflowActionState workflowActionState;

    /**
     * загруженные переменные блока
     */
    ValuesContainer blockVariables;


    /**
     * следующий вызываемый сигнал
     */
    Long nextSignalId;

    /**
     * переменные вызывающего сигнала
     */
    final ValuesContainer callingSignalVariables;

    /**
     * Сигнал который вызывает блок
     */
    final Long callingSignalId;


    /**
     * Текущая загружаемая группа
     */
    @Setter
    int loadingGroup;

    /**
     * Загруженные соурсы
     */
    final Set<Long> loadedSources;


//    public Optional<ProcessValue> getValueById (Long valueId){
//        return variablesContainer.getValueById(valueId);
//    }
//
//    public UUID getScenarioId(){
//        return scenarioId;
//    }

    /**
     *
     * @return текущий вызываемый сигнал
     */
//    public SignalId getCurrentSignalId(){
//        return currentSignalId;
//    }
//
//    public WorkflowActionState getWorkflowState(){
//        return workflowActionState;
//    }
//
    public synchronized void setWorkflowState(WorkflowActionState workflowActionState){
        this.workflowActionState = workflowActionState;
    }

    public synchronized void setBlockVariables(ValuesContainer newBlockVariables){
        if (blockVariables == null){
            blockVariables = newBlockVariables;
            return;
        }
        blockVariables.addValues(newBlockVariables);
    }

    public synchronized void setNextSignalId(Long nextSignalId){
        this.nextSignalId = nextSignalId;
    }



    public void addLoadSource(Long loadSourceId){
        loadedSources.add(loadSourceId);
    }

//    public synchronized void setCurrentSignalId(SignalId currentSignalId){
//        this.currentSignalId = currentSignalId;
//    }
//
//    public ResponseSignalType getSignalResult() {
//        return responseSignalType;
//    }
//
//    public synchronized void setResponseSignalType(ResponseSignalType responseSignalType){
//        this.responseSignalType = responseSignalType;
//    }
//
//    /**
//     * Текущая выполняемая активность
//     * @return
//     */
//    public BlockId getCurrentAction(){
//        return currentAction;
//    }
//
//    public void addVariables(VariablesContainer variablesContainer){
//        variablesContainer.addVariables(variablesContainer);
//    }
//
//
//    public VariablesContainer getCurrentVariables(){
//        return variablesContainer;
//    }
//
//    public ProcessValue getValueByVariableName(String variableName) {
//        return variablesContainer.getValueByName(variableName);
//    }
//
//    public void setValue(ProcessValue processValue) {
//        variablesContainer.addVariable(processValue);
//    }
}
