package com.petra.lib.XXXXXcontext;

import com.petra.lib.environment.context.ProcessValue;
import com.petra.lib.environment.context.variables.VariablesContext;

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
    VariablesContext getDirtyVariablesList();

}
