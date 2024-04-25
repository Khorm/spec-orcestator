package com.petra.lib.workflow.signal;

import com.petra.lib.variable.pure.PureVariableList;
import com.petra.lib.variable.value.ValuesContainer;

import java.util.Collection;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class SignalManager {
    private final Map<Long, SignalModel> signalModelsBySignalIds;

    /**
     * Принимает все модели сигналов которые обрабатывает этот СЕРВИС
     * @param signalBuildModels
     */
    public SignalManager(Collection<SignalBuildModel> signalBuildModels) {
        signalModelsBySignalIds = signalBuildModels
                .stream().map(signalBuildModel -> new SignalModel(signalBuildModel.getSignalId(),
                        new PureVariableList(signalBuildModel.getSignalVariables())))
                .collect(Collectors.toMap(SignalModel::getSignalId, Function.identity()));
    }


    /**
     * Находит айди сигнала по продюсеру, который этот сигнал отправил
     * @param producerVariableContainer
     * @return
     */
    public Signal createParsedSignal(Long signalId, ValuesContainer producerVariableContainer){
        SignalModel signalModel = signalModelsBySignalIds.get(signalId);
        return signalModel.createSignal(producerVariableContainer);
    }

    /**
     * Создается сигнал по входящим параметрам
     * @param signalId
     * @param signalVariables
     * @return
     */
    public Signal createSimpleSignal(Long signalId, ValuesContainer signalVariables){
        return new Signal(signalId, signalVariables);
    }



}
