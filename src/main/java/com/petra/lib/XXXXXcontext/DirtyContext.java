package com.petra.lib.XXXXXcontext;

import com.petra.lib.context.variables.ProcessValue;
import com.petra.lib.context.variables.VariablesContainerImpl;

import java.util.Collection;
import java.util.UUID;

@Deprecated
interface DirtyContext {

    /**
     * @return current scenario id
     */
    UUID getScenarioId();
    /**
     * @param values - filled process values
     */
    void setValues(Collection<ProcessValue> values);
    void setValue(ProcessValue value);
    VariablesContainerImpl getDirtyVariablesList();

}
