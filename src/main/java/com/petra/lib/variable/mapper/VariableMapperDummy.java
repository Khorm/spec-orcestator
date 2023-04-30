package com.petra.lib.variable.mapper;

import com.petra.lib.manager.block.ProcessVariableDto;

import java.util.Collection;

class VariableMapperDummy implements VariableMapper {
    @Override
    public Collection<ProcessVariableDto> map(Collection<ProcessVariableDto> producerCollection) {
        return producerCollection;
    }

    @Override
    public ProcessVariableDto map(ProcessVariableDto producerVariable) {
        return producerVariable;
    }
}
