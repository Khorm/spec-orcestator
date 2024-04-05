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
     * ���� ���������
     */
    final UUID scenarioId;

    /**
     * ���� �������� ������� ������������ ���� ����
     */
    final Long workflowId;

    /**
     * ����������� ����
     */
    final Long execBlock;

    /**
     * ������ �����
     */
    WorkflowActionState workflowActionState;

    /**
     * ����������� ���������� �����
     */
    VariablesContainer blockVariables;

    /**
     * ������ ������� �������� ����
     */
    final Long currentSignalId;

    /**
     * ���������� �������
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
     * @return ������� ���������� ������
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
//     * ������� ����������� ����������
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
