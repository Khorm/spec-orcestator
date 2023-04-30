package com.petra.lib.variable.mapper;


import com.petra.lib.manager.block.ProcessVariableDto;

import java.util.Collection;

/**
 * Парсит переменные из переменных продюсера в переменные консумера
 */
public interface VariableMapper {

    Collection<ProcessVariableDto> map(Collection<ProcessVariableDto> producerCollection);

    ProcessVariableDto map(ProcessVariableDto producerVariable);

}
