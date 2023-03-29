package com.petra.lib.variable.factory;

import com.petra.lib.variable.base.Variable;
import com.petra.lib.variable.base.VariableList;
import com.petra.lib.variable.mapper.VariableMapper;

import java.util.Collection;
import java.util.stream.Collectors;

public final class VariableFactory {

    private VariableFactory(){}

    public static VariableList createVariableList(Collection<VariableModel> variableModels){
        Collection<Variable> variables = variableModels.stream()
                .map(variableModel -> new Variable(variableModel.getId(), variableModel.getName()))
                .collect(Collectors.toList());
        VariableList variableList = new VariableList(variables);
        return variableList;
    }
}
