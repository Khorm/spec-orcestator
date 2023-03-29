package com.petra.lib.variable.factory;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.util.Collection;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
public class VariableModel {
    Long id;
    String name;
    Collection<Long> variableSources;
}
