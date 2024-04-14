package com.petra.lib.workflow.context;

import com.petra.lib.variable.value.VariablesContainer;
import com.petra.lib.workflow.enums.WorkflowActionState;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

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
     * Айди воркфлоу которая обраюатывает этот блок
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
    VariablesContainer blockVariables;


    /**
     * следующий вызываемый сигнал
     */
    Long callingSignalId;

    /**
     * Сигнал который вызывает блок
     */
    final Long currentSignalId;

    /**
     * переменные сигнала
     */
    final VariablesContainer signalVariables;




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

    public synchronized void setBlockVariables(VariablesContainer newBlockVariables){
        if (blockVariables == null){
            blockVariables = newBlockVariables;
            return;
        }
        blockVariables.addVariables(newBlockVariables);
    }

    public synchronized void setCallingSignalId(Long callingSignalId){
        this.callingSignalId = callingSignalId;
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
