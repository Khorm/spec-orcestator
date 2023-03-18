package com.petra.lib.variable.mapper;


import com.petra.lib.variable.process.ProcessVariable;

import java.util.Collection;

/**
 * Парсит переменные из переменных продюсера в переменные консумера
 */
public interface VariableMapper {

    Collection<ProcessVariable> map(Collection<ProcessVariable> producerCollection);

}
