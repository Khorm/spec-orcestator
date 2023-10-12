package com.petra.lib.state.variable.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
public class GroupVariable {
    Long variableId;
    String variableName;
    Long producerVariableId;
    VariableType variableType;
}
