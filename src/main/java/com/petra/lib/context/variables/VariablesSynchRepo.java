package com.petra.lib.context.variables;

import com.petra.lib.state.variable.neww.ProcessValue;

import java.util.Map;
import java.util.UUID;

public interface VariablesSynchRepo {
    void commitValues(Map<Long, ProcessValue> processValueMap, UUID actionId);

}
