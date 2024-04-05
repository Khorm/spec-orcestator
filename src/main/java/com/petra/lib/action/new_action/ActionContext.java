package com.petra.lib.action.new_action;

import com.petra.lib.variable.value.ProcessValue;
import com.petra.lib.variable.value.VariablesContainer;
import com.petra.lib.workflow.SignalId;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.Optional;
import java.util.UUID;

/**
 * ����� �������� ���������� � ������� �����
 */

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class ActionContext {

    /**
     * ID ����������
     */
    final Long actionId;

    /**
     * ���� ��������������
     */
    final UUID scenarioId;

    /**
     * ������� ������ ���������� ����������
     */
    ActionState actionState;

    /**
     * ������� ���������� ����������
     */
    final VariablesContainer actionVariables;


    /**
     * ��������, ������ ������ ������ �� �������������
     */
    final Long requestWorkflowId;
    final String requestServiceName;


    public synchronized void addVariables(VariablesContainer inputVariablesContainer) {
        executingVariables.addVariables(inputVariablesContainer);
    }

    public synchronized void setNewState(ActionState actionState) {
        this.actionState = actionState;
    }

    public synchronized void setOutputSignal(SignalId outputSignalId) {
        this.outputSignalId = outputSignalId;
    }


    public Optional<ProcessValue> getValueById(Long variableId) {
        return executingVariables.getValueById(variableId);
    }

    public ProcessValue getValueByVariableName(String variableName) {
        return executingVariables.getValueByName(variableName);
    }

    public ActionState getState() {
        return actionState;
    }

    public void setValue(ProcessValue value) {
        executingVariables.addVariable(value);
    }

}
