package com.petra.lib.XXXXXcontext;

import com.petra.lib.state.variable.neww.ProcessValue;
import com.petra.lib.context.variables.VariablesContext;
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
    final VariablesContext variablesContext = new VariablesContext(variablesSynchRepo);
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
        variablesContext.setVariableList(value);
    }

    public VariablesContext getVariablesContext() {
        return ;
    }


}
