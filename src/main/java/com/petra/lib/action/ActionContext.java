package com.petra.lib.action;

import com.petra.lib.variable.value.ProcessValue;
import com.petra.lib.variable.value.ValuesContainer;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.Optional;
import java.util.UUID;

/**
 * Общий контекст исполнения в текущем блоке
 */

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class ActionContext {

    /**
     * ID активности
     */
    final Long actionId;

    /**
     * АЙДИ бизнеспроцесса
     */
    final UUID scenarioId;

    /**
     * Текущая стадия выполнения активности
     */
    BlockState actionState;

    /**
     * Текущие переменные активности
     */
    final ValuesContainer actionVariables;


    /**
     * Исходник, откуда пришел сигнал на инициализацию
     */
    final Long requestWorkflowId;
    final String requestServiceName;

    public synchronized void setActionState(BlockState actionState) {
        this.actionState = actionState;
    }

    public BlockState getState() {
        return actionState;
    }

    public void setValue(ProcessValue value) {
        actionVariables.addValue(value);
    }

    public ProcessValue getValueById(Long variableId) {
        return actionVariables.getValueById(variableId);
    }

    public ProcessValue getValueByVariableName(String variableName) {
        return actionVariables.getValueByName(variableName);
    }

}
