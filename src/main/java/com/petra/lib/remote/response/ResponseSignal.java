package com.petra.lib.remote.response;

import com.petra.lib.variable.value.VariablesContainer;
import com.petra.lib.workflow.SignalId;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseSignal {
    SignalId signalId;
    VariablesContainer signalVariables;
}
