package com.petra.lib.variable.mapper;

import java.util.Collection;

public final class VariableMapperFactory {

    private VariableMapperFactory(){}

    public static VariableMapper createVariableMapper(Collection<MapperVariableModel> variableModels) {
        VariableMapCollection variableMapCollection = new VariableMapCollection(variableModels);
        return new VariablesMapperImpl(variableMapCollection);
    }

    public static VariableMapper createDummyMapper(){
        return new VariableMapperDummy();
    }

}
