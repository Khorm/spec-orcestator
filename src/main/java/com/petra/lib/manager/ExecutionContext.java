package com.petra.lib.manager;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.petra.lib.signal.model.SignalTransferModel;
import com.petra.lib.variable.base.VariableList;
import com.petra.lib.variable.process.ProcessVariablesCollection;
import com.petra.lib.variable.process.ProcessVariable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.util.Collection;
import java.util.UUID;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ExecutionContext {
    ProcessVariablesCollection processVariablesCollection;

    @Getter
    SignalTransferModel enterSignalTransferModel;

    ExecutionContext(VariableList variableList, SignalTransferModel enterSignalTransferModel){
        processVariablesCollection = new ProcessVariablesCollection(variableList);
        this.enterSignalTransferModel = enterSignalTransferModel;
//        this.blockId = blockId;
    }

    public synchronized Collection<ProcessVariable> getVariablesList(){
        return processVariablesCollection.getAllVariables();
    }

    public synchronized <T> T getVariableByName(String name, Class<T> clazz) throws JsonProcessingException {
        return processVariablesCollection.getValueByName(name, clazz);
    }


    public synchronized void setVariables(Collection<ProcessVariable> variables, Long signalId){
        asdsa
        variables.forEach(processVariablesCollection::putProcessVariable);
    }

    public synchronized Collection<ProcessVariable> getSignalVariables(){
        return enterSignalTransferModel.getSignalVariables();
    }

    public UUID getScenarioId(){
        return enterSignalTransferModel.getScenarioId();
    }

}
