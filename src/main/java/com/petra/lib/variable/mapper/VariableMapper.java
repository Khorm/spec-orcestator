package com.petra.lib.variable.mapper;


import com.petra.lib.block.ProcessValue;
import com.petra.lib.XXXXXcontext.DirtyVariablesList;

/**
 * Парсит переменные из переменных продюсера в переменные консумера
 */
public interface VariableMapper {

    DirtyVariablesList map(DirtyVariablesList producerCollection);

    ProcessValue map(ProcessValue producerVariable);

}
