package com.petra.lib.manager.block;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.petra.lib.signal.model.SignalTransferModel;
import com.petra.lib.variable.base.VariableList;
import com.petra.lib.variable.mapper.VariableMapper;
import com.petra.lib.variable.process.ProcessVariablesController;
import com.petra.lib.variable.process.ProcessVariable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.transaction.TransactionStatus;

import java.util.Collection;
import java.util.UUID;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ExecutionContext {

    ProcessVariablesController processVariablesController;

    @Getter
    SignalTransferModel enterSignalTransferModel;

    @Getter
    TransactionStatus transactionStatus;


    public ExecutionContext(VariableList variableList, VariableMapper inputValueMap,
                            SignalTransferModel enterSignalTransferModel, TransactionStatus transactionStatus){
        this.transactionStatus = transactionStatus;
        processVariablesController = new ProcessVariablesController(variableList, inputValueMap);
        this.enterSignalTransferModel = enterSignalTransferModel;
    }

    public synchronized Collection<ProcessVariable> getVariablesList(){
        return processVariablesController.getAllVariables();
    }

    public synchronized <T> T getVariableByName(String name, Class<T> clazz) throws JsonProcessingException {
        return processVariablesController.getValueByName(name, clazz);
    }


    public synchronized void setSignalVariables(Collection<ProcessVariable> inputValues){
        processVariablesController.insertOuterVariables(inputValues);
    }

    public synchronized void setLoadVariables(Collection<ProcessVariable> inputValues){
        processVariablesController.setVariables(inputValues);
    }

    public synchronized Collection<ProcessVariable> getSignalVariables(){
        return enterSignalTransferModel.getSignalVariables();
    }

    public UUID getScenarioId(){
        return enterSignalTransferModel.getScenarioId();
    }


    public String getVariablesJson() throws JsonProcessingException {
        return processVariablesController.getVariablesJson();
    }

}
