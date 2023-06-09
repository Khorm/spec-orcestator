package com.petra.lib.worker.variable.model;

import com.petra.lib.variable.mapper.MapperVariableModel;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.util.Collection;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class VariableModel {
    Long variableId;
    String variableName;
}
