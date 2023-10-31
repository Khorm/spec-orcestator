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
 * Общий контекст исполнения в текущем блоке
 */
@Getter
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Builder
public class ActivityContext {

    UUID actionId;

    /**
     * АЙДИ бизнеспроцесса
     */
    UUID businessId;

    /**
     * Айди блока, который его сейчас исполняет
     */
    VersionBlockId currentBlockId;

    /**
     * Текущая транзакция
     */
    Long currentTransactionId;

    /**
     * Имя сервиса в котором исполняетя бизнес процесс
     */
    String currentServiceName;

    /**
     * Айди сервиса в котором исполняетя бизнес процесс
     */
    Long currentServiceId;

    /**
     * Сигнал, ининциализировавший активность в этом блоке
     */
    Signal startSignal;

    /**
     * Текущая стадия выполнения активности
     */
    ActionState currentState;


    /**
     * Текущие переменные активности
     */
    VariablesContext variablesContext;

    PureVariableList pureVariableList;

//    /**
//     * @param businessId           - ади текущего бизнес процесса
//     * @param currentBlockId       - айди текущего исполняемого блока
//     * @param currentServiceName   - имя текущего сериса в котором находить юлок.
//     * @param currentTransactionId - текущая транзакция
//     * @param startSignal          - сигнал инициализировавший активность блока
//     * @param currentState
//     * @param variablesContext     - контекст переменных
//     * @param pureVariableList     - переменные блока
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
