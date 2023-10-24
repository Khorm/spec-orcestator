package com.petra.lib.environment.context;

import com.petra.lib.XXXXXXsignal.SignalId;
import com.petra.lib.environment.context.variables.VariablesContext;
import com.petra.lib.environment.output.enums.RequestType;
import com.petra.lib.block.BlockId;
import com.petra.lib.environment.dto.Signal;
import com.petra.lib.state.ActionState;
import com.petra.lib.variable.pure.PureVariableList;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@Getter

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ActivityContext {
    /**
     * ���� ��������������
     */
    UUID businessId;

    /**
     * ���� �����, ������� ��� ������ ���������
     */
    BlockId currentBlockId;

    /**
     * ��� ������� � ������� ���������� ������ �������
     */
    String currentServiceName;

    /**
     * ���� ���������� �����
     */
    Long currentTransactionId;

    /**
     * ������, ������������������� ���������� � ���� �����
     */
    Signal startSignal;

//    Signal currentSignal;

    /**
     * ������� ���������� ����������
     */
    @Getter
    VariablesContext variablesContext;

    PureVariableList pureVariableList;

    /**
     *
     * @param businessId - ��� �������� ������ ��������
     * @param currentBlockId - ���� �������� ������������ �����
     * @param currentServiceName - ��� �������� ������ � ������� �������� ����.
     * @param currentTransactionId - ������� ����������
     * @param startSignal - ������ ������������������ ���������� �����
//     * @param currentSignal - ������� ������
     * @param variablesContext - �������� ����������
     * @param pureVariableList - ���������� �����
     */
    ActivityContext(UUID businessId, BlockId currentBlockId, String currentServiceName,
                           Long currentTransactionId, Signal startSignal,
                           VariablesContext variablesContext, PureVariableList pureVariableList) {
        this.businessId = businessId;
        this.currentBlockId = currentBlockId;
        this.currentServiceName = currentServiceName;
        this.currentTransactionId = currentTransactionId;
        this.startSignal = startSignal;
//        this.currentSignal = currentSignal;
        this.variablesContext = variablesContext;
        this.pureVariableList = pureVariableList;
    }


    public void syncCurrentInputVariableList(VariablesContext inputVariablesContext){
        variablesContext.syncVariables(inputVariablesContext);
    }

    public void setCurrentState(ActionState actionState){
        variablesContext.setCurrentActionState(actionState);
    }

    public ActionState getCurrentState(){
        return variablesContext.getCurrentActionState();
    }

    public RequestType getRequestType(){
        return startSignal.getRequestType();
    }

    public SignalId getCurrentSignalId(){
        return currentSignal.getSignalId();
    }


}
