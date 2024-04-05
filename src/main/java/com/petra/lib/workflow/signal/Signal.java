package com.petra.lib.workflow.signal;

import com.petra.lib.variable.value.VariablesContainer;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Getter
public class Signal {
    Long signalId;
    VariablesContainer signalVariables;

    Signal(Long signalId, VariablesContainer signalVariables) {
        this.signalId = signalId;
        this.signalVariables = signalVariables;
    }
}
