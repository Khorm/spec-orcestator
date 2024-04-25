package com.petra.lib.workflow.context;

import com.petra.lib.action.BlockState;
import com.petra.lib.variable.value.ValuesContainer;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@RequiredArgsConstructor
public class WorkflowContext {

    /**
     * Айди текущего сценария
     */
    final UUID scenarioId;

    /**
     * айди воркфлоу, которая запрашивает исполнение текущей воркфлоу
     */
    final Long producerId;

    /**
     * айди текущей воркфлоу
     */
    final Long consumerId;

    /**
     * Айди сигнала который запустил исполнение
     */
    final Long signalId;

    /**
     * переменные запустившего воркфлоу сигнала
     */
    final ValuesContainer signalVariables;

    /**
     * стейт в котором находится исполняемая воркфлоу
     */
    BlockState workflowState;

    /**
     * Ответные данные воркфлоу, приходящие из последнего исполненного актиона
     */
    ValuesContainer responseVariables;

    Long responseSignal;

    /**
     * Имя запрашивающего сервиса
     */
    final String requestServiceName;


    public synchronized void setWorkflowState(BlockState workflowState) {
        this.workflowState = workflowState;
    }

    public synchronized void setResponseVariables(ValuesContainer responseVariables) {
        this.responseVariables = responseVariables;
    }


}
