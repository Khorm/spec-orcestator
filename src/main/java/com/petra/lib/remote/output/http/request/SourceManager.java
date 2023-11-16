package com.petra.lib.remote.output.http.request;

import com.petra.lib.block.BlockVersionId;
import com.petra.lib.context.ActivityContext;
import com.petra.lib.context.value.ProcessValue;
import com.petra.lib.context.value.VariableContainerFactory;
import com.petra.lib.context.value.VariablesContainer;
import com.petra.lib.remote.dto.AnswerDto;
import com.petra.lib.remote.input.InputExecuteController;
import com.petra.lib.remote.output.http.SignalManager;
import com.petra.lib.remote.output.http.AnswerDecoder;
import com.petra.lib.remote.output.http.SignalEncoder;
import com.petra.lib.remote.dto.SignalDTO;
import com.petra.lib.remote.signal.SignalType;
import feign.Feign;

import java.util.Collection;
import java.util.Optional;

class SourceManager implements SignalManager {

    InputExecuteController remoteEntryPointAnswer;
    Collection<Long> loaderValIds;

    BlockVersionId sourceBlockId;

    String currentServiceName;

    SourceManager(String sendServiceName, Collection<Long> loaderValIds, BlockVersionId sourceBlockId,
                  String currentServiceName) {
        remoteEntryPointAnswer = Feign.builder()
                .encoder(new SignalEncoder())
                .decoder(new AnswerDecoder())
                .target(InputExecuteController.class, "http://" + sendServiceName + "/execute");
        this.loaderValIds = loaderValIds;
        this.sourceBlockId = sourceBlockId;
        this.currentServiceName = currentServiceName;
    }

    /**
     * Запрашивает инфу у соурса
     * @param activityContext
     * @return
     */
    public AnswerDto send(ActivityContext activityContext) {
        VariablesContainer sourceContainer = createSourceContainer(activityContext);
        SignalDTO signalDTO = new SignalDTO(activityContext.getScenarioId(),
                sourceBlockId, sourceContainer, currentServiceName,
                activityContext.getCurrentBlockId(), SignalType.REQUEST_SOURCE);
        return remoteEntryPointAnswer.execute(signalDTO);
    }

    /**
     * Преобразует переменные текущей активности
     * в переменные сигнала. Достает только нужные сигналу значения переменных
     * @param activityContext
     * @return
     */
    private VariablesContainer createSourceContainer(ActivityContext activityContext) {
        VariablesContainer sourceContainer = VariableContainerFactory.getSimpleVariableContainer();
        for (Long loaderId : loaderValIds) {
            Optional<ProcessValue> value = activityContext.getValueById(loaderId);
            if (value.isEmpty()) throw new NullPointerException("Context variable "
                    + activityContext.getPureVariable(loaderId).getName()
                    + " not ready for sending to source");
            sourceContainer.addVariable(value.get());
        }
        return sourceContainer;
    }
}
