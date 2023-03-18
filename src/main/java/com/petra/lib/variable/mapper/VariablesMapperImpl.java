package com.petra.lib.variable.mapper;

import com.petra.lib.variable.process.ProcessVariable;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Класс непосредственно переделывающий входящую коллекцию переменных в исходящую
 */

@RequiredArgsConstructor
class VariablesMapperImpl implements VariableMapper{

    /**
     * Коллекция переменых владельца
     */
    private final VariableMapCollection consumerVariableCollection;

    @Override
    public Collection<ProcessVariable> map(Collection<ProcessVariable> producerVariableCollection) {
        Collection<ProcessVariable> consumerVariables = new ArrayList<>();
        for (ProcessVariable producerVariable : producerVariableCollection) {
            Long consumerVariableId = consumerVariableCollection.findConsumerVariableByProducerVariable(producerVariable.getId());
            ProcessVariable consumerProcessVariable = new ProcessVariable(consumerVariableId, producerVariable.getValue());
            consumerVariables.add(consumerProcessVariable);
        }
        return consumerVariables;
    }


    /**
     * Заполнить переменные процесса из входящих переменных
     * @param sourceValueList
     * Входящщие переменные процесса
     * @param ownerCollection
     * Переменнаы процесса владельца
     */
//    private void mapProcessCollection(Collection<ProcessVariable> producerValueList, ProcessVariablesCollection ownerCollection) {
//        for (ProcessVariable sourceVariable : sourceValueList) {
//            Variable ownerVariable = variableMapCollection.findVariableBySource(sourceVariable);
//            ProcessVariable ownerProcessVariable = ownerVariable.createProcessVariable(sourceVariable.getValue());
//            ownerCollection.putProcessVariable(ownerProcessVariable);
//        }
//    }
}
