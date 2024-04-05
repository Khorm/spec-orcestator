package com.petra.lib.workflow.signal;

import com.petra.lib.variable.value.VariablesContainer;

import java.util.Map;

public class SignalManager {
    private final Map<Long, SignalModel> signalModelsByProducerIds;

    /**
     * Находит айди сигнала по продюсеру, который этот сигнал отправил
     * @param producerBlockId
     * @param producerVariableContainer
     * @return
     */
    public Signal createParsedSignal(Long producerBlockId, VariablesContainer producerVariableContainer){
        SignalModel signalModel = signalModelsByProducerIds.get(producerBlockId);
        return signalModel.createSignal(producerBlockId, producerVariableContainer);
    }

    /**
     * Создается сигнал по входящим параметрам
     * @param signalId
     * @param signalVariables
     * @return
     */
    public Signal createSimpleSignal(Long signalId, VariablesContainer signalVariables){
        return new Signal(signalId, signalVariables);
    }



}
