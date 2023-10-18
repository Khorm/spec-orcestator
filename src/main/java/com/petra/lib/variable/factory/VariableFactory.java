package com.petra.lib.variable.factory;

import com.petra.lib.variable.base.StatelessVariable;
import com.petra.lib.variable.base.PureVariableList;

import java.util.Collection;
import java.util.stream.Collectors;

public final class VariableFactory {

    private VariableFactory(){}

    public static PureVariableList createVariableList(Collection<VariableModel> variableModels){
        Collection<StatelessVariable> statelessVariables = variableModels.stream()
                .map(variableModel -> new StatelessVariable(variableModel.getId(), variableModel.getName()))
                .collect(Collectors.toList());
        PureVariableList pureVariableList = new PureVariableList(statelessVariables);
        return pureVariableList;
    }
}
