package com.petra.lib.source;

import com.petra.lib.variable.value.ProcessValue;
import com.petra.lib.variable.value.ValuesContainer;
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
    final ValuesContainer arguments;

    /**
     * Исходник, откуда пришел сигнал на инициализацию
     */
//    final BlockId requestBlockId;
//    final String requestServiceName;
    ValuesContainer resultVariables;
    final Long requestingWorkflowId;
    final String workflowServiceName;
    final Long requestingBlockId;


//    public synchronized void addVariables(VariablesContainer inputVariablesContainer) {
//        executingVariables.addVariables(inputVariablesContainer);
//    }

    public ProcessValue getValueByVariableName(String variableName) {
        return arguments.getValueByName(variableName);
    }

    public synchronized void setResult(ValuesContainer resultVariables) {
        this.resultVariables = resultVariables;
    }

//    public void setValue(ProcessValue value) {
//        executingVariables.addVariable(value);
//    }

}
