package com.petra.lib.context;

import com.petra.lib.block.BlockVersionId;
import com.petra.lib.context.state.ActionStateContext;
import com.petra.lib.context.value.ProcessValue;
import com.petra.lib.context.value.VariablesContainer;
import com.petra.lib.state.ActionState;
import com.petra.lib.remote.signal.SignalType;
import com.petra.lib.variable.pure.PureVariable;
import com.petra.lib.variable.pure.PureVariableList;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.util.Optional;
import java.util.UUID;

/**
 * ����� �������� ���������� � ������� �����
 */

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Builder
public class ActivityContext {

    /**
     * ID ����������
     */
    UUID actionId;

    /**
     * ���� ��������������
     */
    @Getter
    UUID scenarioId;

    /**
     * ������� ����������
     */
    Long currentTransactionId;

    /**
     * ������� ������ ���������� ����������
     */
    ActionStateContext actionStateContext;

    /**
     * ������� ���������� ����������
     */
    @Getter
    VariablesContainer contextVariablesContainer;

    /**
     * ������� ������
     */
    RequestSignal requestSignal;

    @Getter
    PureVariableList activityVariables;


    /**
     * ��������, ������ ������ ������ �� �������������
     *
     */
    @Getter
    BlockVersionId requestBlockId;
    @Getter
    String requestServiceName;


    /**
     * ���� ��������� ������
     *
     */
    @Getter
    BlockVersionId currentBlockId;
    @Getter
    String currentServiceName;

    @Getter
    BlockVersionId currentActionBlock;








//    /**
//     * @param businessId           - ��� �������� ������ ��������
//     * @param currentBlockId       - ���� �������� ������������ �����
//     * @param currentServiceName   - ��� �������� ������ � ������� �������� ����.
//     * @param currentTransactionId - ������� ����������
//     * @param startSignal          - ������ ������������������ ���������� �����
//     * @param currentState
//     * @param variablesContext     - �������� ����������
//     * @param pureVariableList     - ���������� �����
//     */
//    ActivityContext(UUID businessId, BlockId currentBlockId, String currentServiceName,
//                    Long currentTransactionId, Signal startSignal,
//                    ActionState currentState, VariablesContext variablesContext, PureVariableList pureVariableList) {
//        this.businessId = businessId;
//        this.currentBlockId = currentBlockId;
//        this.currentServiceName = currentServiceName;
//        this.currentTransactionId = currentTransactionId;
//        this.startSignal = startSignal;
//        this.currentState = currentState;
//        this.variablesContext = variablesContext;
//        this.pureVariableList = pureVariableList;
//    }




    public void addVariables(VariablesContainer inputVariablesContainer){
        contextVariablesContainer.addVariables(inputVariablesContainer, actionId);
    }

    public VariablesContainer getRequestSignalVariables(){
        return requestSignal.getSignalContainer();
    }

    public void setNewState(ActionState actionState){
        actionStateContext.setCurrentState(actionState);
    }

//    public VariablesContainer getContextVariables(){
//        return contextVariablesContainer;
//    }

    public Optional<ProcessValue> getValueById(Long variableId){
        return contextVariablesContainer.getValueById(variableId);
    }

    public ProcessValue getValueByVariableName(String variableName){
        return contextVariablesContainer.getValueByName(variableName);
    }

    public ActionState getState(){
        return actionStateContext.getState();
    }

    public SignalType getSignalType(){
        return requestSignal.getSignalType();
    }

    public void setValue(ProcessValue value){
        contextVariablesContainer.addVariable(value, actionId);
    }

    public BlockVersionId getRequestSignalId(){
        return requestSignal.getBlockVersionId();
    }

    public String getRequestSignalName(){
        return requestSignal.getSignalName();
    }

    public SignalType getRequestSignalType(){
        return requestSignal.getSignalType();
    }

    public PureVariable getPureVariable(Long id){

    }


}
