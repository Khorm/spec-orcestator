package com.petra.lib.variable.mapper;

import com.petra.lib.block.ProcessValue;

import java.util.Collection;

class VariableMapperDummy implements VariableMapper {
    @Override
    public Collection<ProcessValue> map(Collection<ProcessValue> producerCollection) {
        return producerCollection;
    }

    @Override
    public ProcessValue map(ProcessValue producerVariable) {
        return producerVariable;
    }
}
