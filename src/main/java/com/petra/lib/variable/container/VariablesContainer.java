package com.petra.lib.variable.container;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

public interface VariablesContainer {
    /**
     * ��������� ����� �������� ��� �������� ������.
     * @param processValue
     */
    void addVariable(ProcessValue processValue, UUID actionId);

    /**
     * �������������� �������� ����� � �������� ����������.
     * �������� ������������� ��������
     * @param inputContext
     */
    void addVariables(VariablesContainer inputContext, UUID actionId);

    ProcessValue getValueByName(String name);

    Optional<ProcessValue> getValueById(Long id);

    Collection<ProcessValue> getValues();


}
