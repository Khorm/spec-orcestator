package com.petra.lib.manager.block;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.petra.lib.signal.model.SignalTransferModel;
import com.petra.lib.variable.base.VariableList;
import com.petra.lib.variable.mapper.VariableMapper;
import com.petra.lib.variable.process.ProcessVariablesController;
import com.petra.lib.variable.process.ProcessVariable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import org.springframework.transaction.TransactionStatus;

import java.util.Collection;
import java.util.UUID;

/**
 * Job context during execution
 */
@ToString(onlyExplicitlyIncluded = true)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class JobContext {

    ProcessVariablesController processVariablesController;

    /**
     * Request signal model
     */
    @Getter
    SignalTransferModel requestSignalTransferModel;

    @Getter
    TransactionStatus transactionStatus;


    public JobContext(VariableList variableList, VariableMapper inputValueMap,
                      SignalTransferModel requestSignalTransferModel, TransactionStatus transactionStatus){
        this.transactionStatus = transactionStatus;
        processVariablesController = new ProcessVariablesController(variableList, inputValueMap);
        this.requestSignalTransferModel = requestSignalTransferModel;
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
        return requestSignalTransferModel.getSignalVariables();
    }

    public UUID getScenarioId(){
        return requestSignalTransferModel.getScenarioId();
    }


    public String getVariablesJson() throws JsonProcessingException {
        return processVariablesController.getVariablesJson();
    }

}
