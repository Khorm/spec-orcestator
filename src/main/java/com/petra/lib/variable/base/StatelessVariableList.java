package com.petra.lib.variable.base;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

import java.util.Collection;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Variable list for action or signal
 */
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class StatelessVariableList {
    Map<Long, StatelessVariable> idVariableMap;
    Map<String, StatelessVariable> nameVariableMap;

    public StatelessVariableList(Collection<StatelessVariable> statelessVariables) {
        idVariableMap = statelessVariables.stream()
                .collect(Collectors.toMap(StatelessVariable::getId, Function.identity()));
        nameVariableMap = statelessVariables.stream()
                .collect(Collectors.toMap(StatelessVariable::getName, Function.identity()));
    }

    public StatelessVariable getVariableById(Long id) {
        return idVariableMap.get(id);
    }

    public StatelessVariable getVariableByName(String name) {
        return nameVariableMap.get(name);
    }
}
