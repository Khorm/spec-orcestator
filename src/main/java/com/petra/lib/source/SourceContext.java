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
     * ID ����������
     */
//    final Long actionId;

    /**
     * ID �������� ������
     */
    final Long sourceId;

    /**
     * ���� ��������������
     */
    final UUID scenarioId;


    /**
     * ������� ���������� ������
     */
    final VariablesContainer arguments;

    /**
     * ��������, ������ ������ ������ �� �������������
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
