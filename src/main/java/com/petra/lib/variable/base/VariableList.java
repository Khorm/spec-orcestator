package com.petra.lib.variable.base;

import lombok.RequiredArgsConstructor;

import java.util.Collection;

@RequiredArgsConstructor
public class VariableList {

    private final Collection<Variable> variables;

    public Variable getVariableById(Long id) {
        for (Variable variable : variables) {
            if (variable.getId().equals(id)) {
                return variable;
            }
        }
        throw new NullPointerException("Variable not found");
    }

    public Variable getVariableByName(String name) {
        for (Variable variable : variables) {
            if (variable.getName().equals(name)) {
                return variable;
            }
        }
        throw new NullPointerException("Variable not found");
    }
}
