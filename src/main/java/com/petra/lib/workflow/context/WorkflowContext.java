package com.petra.lib.workflow.context;

import com.petra.lib.action.BlockState;
import com.petra.lib.variable.value.VariablesContainer;
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
     * переменные запустившего сигнала
     */
    final VariablesContainer signalVariables;

    /**
     * стейт в котором находится исполняемая воркфлоу
     */
    BlockState workflowState;

    /**
     * Ответные данные воркфлоу
     */
    VariablesContainer responseVariables;

    /**
     * Имя запрашивающего сервиса
     */
    final String requestServiceName;


    public synchronized void setWorkflowState(BlockState workflowState) {
        this.workflowState = workflowState;
    }

    public synchronized void setResponseVariables(VariablesContainer responseVariables) {
        this.responseVariables = responseVariables;
    }


}
