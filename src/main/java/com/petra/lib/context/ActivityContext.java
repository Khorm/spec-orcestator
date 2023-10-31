package com.petra.lib.context;

import com.petra.lib.context.variables.VariablesContext;
import com.petra.lib.block.VersionBlockId;
import com.petra.lib.environment.dto.Signal;
import com.petra.lib.state.ActionState;
import com.petra.lib.variable.pure.PureVariableList;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

/**
 * ����� �������� ���������� � ������� �����
 */
@Getter
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Builder
public class ActivityContext {

    UUID actionId;

    /**
     * ���� ��������������
     */
    UUID businessId;

    /**
     * ���� �����, ������� ��� ������ ���������
     */
    VersionBlockId currentBlockId;

    /**
     * ������� ����������
     */
    Long currentTransactionId;

    /**
     * ��� ������� � ������� ���������� ������ �������
     */
    String currentServiceName;

    /**
     * ���� ������� � ������� ���������� ������ �������
     */
    Long currentServiceId;

    /**
     * ������, ������������������� ���������� � ���� �����
     */
    Signal startSignal;

    /**
     * ������� ������ ���������� ����������
     */
    ActionState currentState;


    /**
     * ������� ���������� ����������
     */
    VariablesContext variablesContext;

    PureVariableList pureVariableList;

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




    public void syncCurrentInputVariableList(VariablesContext inputVariablesContext){
        variablesContext.syncVariables(inputVariablesContext);
    }

    public VariablesContext getSignalVariablesContext(){
        return startSignal.getVariablesContext();
    }



//    public void setCurrentState(ActionState actionState){
//        variablesContext.setCurrentActionState(actionState);
//    }
//
//    public ActionState getCurrentState(){
//        return variablesContext.getCurrentActionState();
//    }
//
//    public RequestType getRequestType(){
//        return startSignal.getRequestType();
//    }
//
//    public SignalId getCurrentSignalId(){
//        return currentSignal.getSignalId();
//    }


}
