package com.petra.lib.variable.mapper;


import com.petra.lib.environment.context.ProcessValue;
import com.petra.lib.environment.context.variables.VariablesContext;

/**
 * Парсит переменные из переменных продюсера в переменные консумера
 */
public interface VariableMapper {

    VariablesContext map(VariablesContext producerCollection);

    ProcessValue map(ProcessValue producerVariable);

}
