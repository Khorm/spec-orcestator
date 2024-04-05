package com.petra.lib.source;

import com.petra.lib.variable.value.ProcessValue;
import com.petra.lib.variable.value.VariablesContainer;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
@Getter
public class SourceContext {
    /**
     * ID активности
     */
//    final Long actionId;

    /**
     * ID текущего соурса
     */
    final Long sourceId;

    /**
     * АЙДИ бизнеспроцесса
     */
    final UUID scenarioId;


    /**
     * Текущие переменные соурса
     */
    final VariablesContainer arguments;

    /**
     * Исходник, откуда пришел сигнал на инициализацию
     */
//    final BlockId requestBlockId;
//    final String requestServiceName;
    VariablesContainer resultVariables;

//    public synchronized void addVariables(VariablesContainer inputVariablesContainer) {
//        executingVariables.addVariables(inputVariablesContainer);
//    }

    public ProcessValue getValueByVariableName(String variableName) {
        return arguments.getValueByName(variableName);
    }

    public synchronized void setResult(VariablesContainer resultVariables) {
        this.resultVariables = resultVariables;
    }

//    public void setValue(ProcessValue value) {
//        executingVariables.addVariable(value);
//    }

}
