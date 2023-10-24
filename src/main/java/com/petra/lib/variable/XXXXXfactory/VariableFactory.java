package com.petra.lib.variable.XXXXXfactory;

import com.petra.lib.variable.pure.PureVariable;
import com.petra.lib.variable.pure.PureVariableList;

import java.util.Collection;
import java.util.stream.Collectors;

@Deprecated
public final class VariableFactory {

    private VariableFactory(){}

    public static PureVariableList createVariableList(Collection<VariableModel> variableModels){
        Collection<PureVariable> pureVariables = variableModels.stream()
                .map(variableModel -> new PureVariable(variableModel.getId(), variableModel.getName()))
                .collect(Collectors.toList());
        PureVariableList pureVariableList = new PureVariableList(pureVariables);
        return pureVariableList;
    }
}
