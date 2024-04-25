package com.petra.lib.workflow.signal;

import com.petra.lib.variable.pure.PureVariableList;
import com.petra.lib.variable.value.ProcessValue;
import com.petra.lib.variable.value.ValuesContainer;
import com.petra.lib.variable.value.ValuesContainerFactory;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.util.Collection;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
class SignalModel {

    @Getter
    Long signalId;

    /**
     * Хранит мапперы в переменные сигнала по айди продюсера сигнала
     */
//    Map<Long, PureVariableList> signalValuesListByProducerBlockId;

    /**
     * Хранит переменные сигнала
     */
    PureVariableList signalVariablesList;

    SignalModel(Long signalId, PureVariableList signalVariablesList) {
        this.signalId = signalId;
        this.signalVariablesList = signalVariablesList;
    }

    public Signal createSignal(ValuesContainer producerVariableContainer) {

        Collection<ProcessValue> signalValues = signalVariablesList
                .parseVariables(producerVariableContainer.getValues());
        return new Signal(signalId, ValuesContainerFactory.createValuesContainer(signalValues));
    }


}
