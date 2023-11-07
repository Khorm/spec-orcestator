package com.petra.lib.XXXXXcontext;

import com.petra.lib.context.variables.ProcessValue;
import com.petra.lib.context.variables.VariablesContainerImpl;
import com.petra.lib.variable.pure.PureVariableList;
import lombok.AccessLevel;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

import java.util.Collection;
import java.util.UUID;

/**
 * Job context during execution
 * Контекст активности
 */
@FieldDefaults(level = AccessLevel.PRIVATE)
@ToString
class DutryContextImpl implements DirtyContext {

    //набор переменных контектста
    final VariablesContainerImpl variablesContainerImpl = new VariablesContainerImpl(variablesSynchRepo, actionId);
    //чистый набор переменных
    final PureVariableList pureVariableList;
    UUID scenarioId;


    /**
     *
     * @param pureVariableList - list
     */
    DutryContextImpl( PureVariableList pureVariableList) {
        this.pureVariableList = pureVariableList;

    }

    @Override
    public UUID getScenarioId() {
        return scenarioId;
    }

    @Override
    public void setValues(Collection<ProcessValue> values) {

    }


    @Override
    public void setValue(ProcessValue value) {
        variablesContainerImpl.setVariableList(value);
    }

    public VariablesContainerImpl getVariablesContainerImpl() {
        return ;
    }


}
