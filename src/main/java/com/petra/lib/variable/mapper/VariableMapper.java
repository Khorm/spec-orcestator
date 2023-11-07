package com.petra.lib.variable.mapper;


import com.petra.lib.context.variables.VariablesContainer;
import com.petra.lib.context.variables.ProcessValue;

/**
 * Парсит переменные из переменных продюсера в переменные консумера
 */
public interface VariableMapper {

    VariablesContainer map(VariablesContainer producerCollection);

    ProcessValue map(ProcessValue producerVariable);

}
