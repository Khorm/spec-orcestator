package com.petra.lib.variable.mapper;

import com.petra.lib.variable.process.ProcessVariable;

import java.util.Collection;

class VariableMapperDummy implements VariableMapper{
    @Override
    public Collection<ProcessVariable> map(Collection<ProcessVariable> producerCollection) {
        return producerCollection;
    }
}
