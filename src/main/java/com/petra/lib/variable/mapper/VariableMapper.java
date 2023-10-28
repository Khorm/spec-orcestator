package com.petra.lib.variable.mapper;


import com.petra.lib.state.variable.neww.ProcessValue;
import com.petra.lib.context.variables.VariablesContext;

/**
 * Парсит переменные из переменных продюсера в переменные консумера
 */
public interface VariableMapper {

    VariablesContext map(VariablesContext producerCollection);

    ProcessValue map(ProcessValue producerVariable);

}
