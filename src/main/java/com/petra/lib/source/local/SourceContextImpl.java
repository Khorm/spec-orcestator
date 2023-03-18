package com.petra.lib.source.local;

import com.petra.lib.variable.process.ProcessVariable;
import com.petra.lib.variable.process.ProcessVariablesCollection;
import com.petra.lib.variable.base.Variable;
import com.petra.lib.variable.mapper.VariableMapCollection;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.Collection;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class SourceContextImpl /*implements SourceContext*/{

//    /**
//     * Переменные активности
//     */
//    VariableMapCollection sourceVariableCollection;
//
//    /**
//     * Переменные компенсации
//     */
//    ProcessVariablesCollection sourceProcessVariablesCollection;
//
//
//    @Override
//    public <T> T getVariable(String variableName) {
//        return (T) sourceProcessVariablesCollection.getValueByName(variableName);
//    }
//
//    @Override
//    public void setVariable(String name, Object value) {
//        Variable variable = sourceVariableCollection.getByName(name);
//        sourceProcessVariablesCollection.putProcessVariable(variable.createProcessVariable(value));
//    }
//
//    Collection<ProcessVariable> getProcessVariables(){
//        return sourceProcessVariablesCollection.getAllVariables();
//    }
}
