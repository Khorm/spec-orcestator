package com.petra.lib.manager;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.petra.lib.signal.model.SignalTransferModel;
import com.petra.lib.variable.base.VariableList;
import com.petra.lib.variable.mapper.VariableMapper;
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

    @Getter
    Integer group;


    ExecutionContext(VariableList variableList, VariableMapper inputValueMap, SignalTransferModel enterSignalTransferModel, Integer group){
        processVariablesCollection = new ProcessVariablesCollection(variableList, inputValueMap);
        this.group = group;
        this.enterSignalTransferModel = enterSignalTransferModel;
    }

    public synchronized Collection<ProcessVariable> getVariablesList(){
        return processVariablesCollection.getAllVariables();
    }

    public synchronized <T> T getVariableByName(String name, Class<T> clazz) throws JsonProcessingException {
        return processVariablesCollection.getValueByName(name, clazz);
    }


    public synchronized void setVariables(Collection<ProcessVariable> inputValues){
        processVariablesCollection.insertOuterVariables(inputValues);
    }

    public synchronized Collection<ProcessVariable> getSignalVariables(){
        return enterSignalTransferModel.getSignalVariables();
    }

    public UUID getScenarioId(){
        return enterSignalTransferModel.getScenarioId();
    }


    public String getVariablesJson() throws JsonProcessingException {
        return processVariablesCollection.getVariablesJson();
    }


}
