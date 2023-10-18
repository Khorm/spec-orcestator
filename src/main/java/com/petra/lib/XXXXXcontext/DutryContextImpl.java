package com.petra.lib.XXXXXcontext;

import com.petra.lib.block.ProcessValue;
import com.petra.lib.variable.base.PureVariableList;
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
    final DirtyVariablesList dirtyVariablesList = new DirtyVariablesList();
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
        dirtyVariablesList.setVariableList(value);
    }

    @Override
    public DirtyVariablesList getDirtyVariablesList() {
        return ;
    }


}
