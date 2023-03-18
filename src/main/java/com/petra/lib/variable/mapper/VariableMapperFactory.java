package com.petra.lib.variable.mapper;

import com.petra.lib.variable.model.VariableModel;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public final class VariableMapperFactory {

    public static VariableMapper createVariableMapper(Collection<VariableModel> variableModels){

        Map<Long, Long> variableMap = new HashMap<>();
        for (VariableModel variableModel : variableModels){
            for (Long consumerVariable: variableModel.getConsumerVariables()){
                variableMap.put(variableModel.getId(), consumerVariable);
            }
        }
        VariableMapCollection variableMapCollection = new VariableMapCollection(variableMap);

        return new VariablesMapperImpl(variableMapCollection);
    }

}
