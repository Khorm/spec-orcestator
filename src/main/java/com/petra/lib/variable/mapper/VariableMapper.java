package com.petra.lib.variable.mapper;


import com.petra.lib.context.value.VariablesContainer;
import com.petra.lib.context.value.ProcessValue;

/**
 * Парсит переменные из переменных продюсера в переменные консумера
 */
public interface VariableMapper {

    /**
     * Из полученной коллекции производителя преобразовывает список переменных в
     * коллекцию потребителя
     * @param producerCollection - коллекция производителя
     * @return - коллекция потребителя
     */
    VariablesContainer map(VariablesContainer producerCollection);

    ProcessValue map(ProcessValue producerVariable);

}
