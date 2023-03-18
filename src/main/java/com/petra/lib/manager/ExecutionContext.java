package com.petra.lib.manager;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.petra.lib.signal.model.SignalModel;
import com.petra.lib.variable.base.VariableList;
import com.petra.lib.variable.process.ProcessVariablesCollection;
import com.petra.lib.variable.process.ProcessVariable;
import com.petra.lib.signal.model.Version;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.Collection;
import java.util.UUID;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ExecutionContext {
    ProcessVariablesCollection processVariablesCollection;

//    @Getter
//    Long blockId;

    @Getter
    SignalModel enterSignalModel;

    ExecutionContext(VariableList variableList, SignalModel enterSignalModel){
        processVariablesCollection = new ProcessVariablesCollection(variableList);
        this.enterSignalModel = enterSignalModel;
//        this.blockId = blockId;
    }

    public synchronized Collection<ProcessVariable> getVariablesList(){
        return processVariablesCollection.getAllVariables();
    }

    public synchronized <T> T getVariableByName(String name, Class<T> clazz) throws JsonProcessingException {
        return processVariablesCollection.getValueByName(name, clazz);
    }


    public synchronized void setVariables(Collection<ProcessVariable> variables){
        variables.forEach(processVariablesCollection::putProcessVariable);
    }

    public synchronized Collection<ProcessVariable> getSignalVariables(){
        return enterSignalModel.getSignalVariables();
    }

}
