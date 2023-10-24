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
     * АЙДИ бизнеспроцесса
     */
    UUID businessId;

    /**
     * Айди блока, который его сейчас исполняет
     */
    BlockId currentBlockId;

    /**
     * Имя сервиса в котором исполняетя бизнес процесс
     */
    String currentServiceName;

    /**
     * Айди транзакции блока
     */
    Long currentTransactionId;

    /**
     * Сигнал, ининциализировавший активность в этом блоке
     */
    Signal startSignal;

//    Signal currentSignal;

    /**
     * Текущие переменные активности
     */
    @Getter
    VariablesContext variablesContext;

    PureVariableList pureVariableList;

    /**
     *
     * @param businessId - ади текущего бизнес процесса
     * @param currentBlockId - айди текущего исполняемого блока
     * @param currentServiceName - имя текущего сериса в котором находить юлок.
     * @param currentTransactionId - текущая транзакция
     * @param startSignal - сигнал инициализировавший активность блока
//     * @param currentSignal - текущий сигнал
     * @param variablesContext - контекст переменных
     * @param pureVariableList - переменные блока
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
