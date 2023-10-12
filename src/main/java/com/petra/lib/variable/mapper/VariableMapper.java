package com.petra.lib.variable.mapper;


import com.petra.lib.block.ProcessValue;

import java.util.Collection;

/**
 * Парсит переменные из переменных продюсера в переменные консумера
 */
public interface VariableMapper {

    Collection<ProcessValue> map(Collection<ProcessValue> producerCollection);

    ProcessValue map(ProcessValue producerVariable);

}
