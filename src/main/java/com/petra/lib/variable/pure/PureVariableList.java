package com.petra.lib.variable.pure;

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
public class PureVariableList {
    Map<Long, PureVariable> idVariableMap;
    Map<String, PureVariable> nameVariableMap;

    public PureVariableList(Collection<PureVariable> pureVariables) {
        idVariableMap = pureVariables.stream()
                .collect(Collectors.toMap(PureVariable::getId, Function.identity()));
        nameVariableMap = pureVariables.stream()
                .collect(Collectors.toMap(PureVariable::getName, Function.identity()));
    }

    public PureVariable getVariableById(Long id) {
        return idVariableMap.get(id);
    }

    public PureVariable getVariableByName(String name) {
        return nameVariableMap.get(name);
    }
}
