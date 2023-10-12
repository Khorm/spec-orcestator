package com.petra.lib.state.variable.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Deprecated
public class LoaderModel {
    Long variableId;
    String variableName;
}
