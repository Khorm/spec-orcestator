package com.petra.lib.context.variables;

import java.util.Optional;

public interface VariablesContainer {
    /**
     * ��������� ����� �������� ��� �������� ������.
     * @param processValue
     */
    void addVariable(ProcessValue processValue);

    /**
     * �������������� �������� ����� � �������� ����������.
     * �������� ������������� ��������
     * @param inputContext
     */
    void addVariables(VariablesContainer inputContext);

    ProcessValue getValueByName(String name);

    Optional<ProcessValue> getValueById(Long id);
}
