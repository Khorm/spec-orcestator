package com.petra.lib.workflow.signal;

import com.petra.lib.variable.value.ValuesContainer;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Getter
public class Signal {
    Long signalId;
    ValuesContainer signalVariables;

    Signal(Long signalId, ValuesContainer signalVariables) {
        this.signalId = signalId;
        this.signalVariables = signalVariables;
    }
}
