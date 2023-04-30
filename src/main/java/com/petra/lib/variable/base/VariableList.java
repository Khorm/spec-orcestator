package com.petra.lib.variable.base;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

import java.util.Collection;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class VariableList {
    Map<Long, Variable> idVariableMap;
    Map<String, Variable> nameVariableMap;

    public VariableList(Collection<Variable> variables) {
        idVariableMap = variables.stream()
                .collect(Collectors.toMap(Variable::getId, Function.identity()));
        nameVariableMap = variables.stream()
                .collect(Collectors.toMap(Variable::getName, Function.identity()));
    }

    public Variable getVariableById(Long id) {
        return idVariableMap.get(id);
    }

    public Variable getVariableByName(String name) {
        return nameVariableMap.get(name);
    }
}
