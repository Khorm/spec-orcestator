package com.petra.lib.action;

import com.petra.lib.variable.process.ProcessVariablesCollection;
import lombok.RequiredArgsConstructor;

@Deprecated
@RequiredArgsConstructor
public class CompensationContextImpl implements CompensationContext {

    private final ProcessVariablesCollection compensationVariableCollection;

    @Override
    public Object getCompensationValue(String variableName) {
        return compensationVariableCollection.getValueByName(variableName);
    }
}
