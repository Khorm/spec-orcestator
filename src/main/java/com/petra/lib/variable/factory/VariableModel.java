package com.petra.lib.variable.factory;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

import java.util.Collection;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@ToString
@Deprecated
public class VariableModel {
    Long id;
    String name;
    Collection<Long> variableSources;
}
