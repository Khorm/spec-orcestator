package com.petra.lib.XXXXXcontext;

import com.petra.lib.block.ProcessValue;

import java.util.Collection;
import java.util.UUID;

@Deprecated
public interface DirtyContext {

    /**
     * @return current scenario id
     */
    UUID getScenarioId();
    /**
     * @param values - filled process values
     */
    void setValues(Collection<ProcessValue> values);
    void setValue(ProcessValue value);
    DirtyVariablesList getDirtyVariablesList();

}
