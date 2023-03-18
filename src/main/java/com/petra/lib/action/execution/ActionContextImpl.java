package com.petra.lib.action.execution;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.petra.lib.manager.ExecutionContext;
import com.petra.lib.variable.process.ProcessVariablesCollection;
import com.petra.lib.variable.base.Variable;
import com.petra.lib.variable.base.VariableList;
import com.petra.lib.variable.process.ProcessVariable;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ActionContextImpl implements ActionContext {

    /**
     * Переменные компенсации
     */
    ProcessVariablesCollection compensationProcessVariablesCollection;

    /**
     * Список переменных для компенсации
     */
    VariableList compensationVariableList;
    ExecutionContext executionContext;

    ActionContextImpl(VariableList actionVariableList, ExecutionContext executionContext, VariableList compensationVariableList) {
        this.compensationProcessVariablesCollection = new ProcessVariablesCollection(executionContext.getBlockId(), actionVariableList);
        this.compensationVariableList = compensationVariableList;
        this.executionContext = executionContext;
    }


    @Override
    public <T> T getExecutionVariable(String variableName, Class<T> clazz) throws JsonProcessingException {
        return (T) executionContext.getVariableByName(variableName, clazz);
    }

    @Override
    public void setCompensationVariable(String name, Object value) {
        Variable variable = compensationVariableList.getVariableByName(name);
        ProcessVariable processVariable = new ProcessVariable(variable.getId(), value);
        compensationProcessVariablesCollection.putProcessVariable(processVariable);
    }

    ExecutionContext getExecutionContext() {
        return executionContext;
    }
}
