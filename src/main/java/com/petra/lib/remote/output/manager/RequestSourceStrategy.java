package com.petra.lib.remote.output.manager;

import com.petra.lib.block.BlockVersionId;
import com.petra.lib.context.ActivityContext;
import com.petra.lib.context.value.ProcessValue;
import com.petra.lib.context.value.VariableContainerFactory;
import com.petra.lib.context.value.VariablesContainer;
import com.petra.lib.remote.dto.AnswerDto;
import com.petra.lib.remote.output.http.AnswerDecoder;
import com.petra.lib.remote.output.http.SignalEncoder;
import com.petra.lib.remote.output.http.answer.AnswerConsumer;
import com.petra.lib.remote.signal.SignalType;
import com.petra.lib.variable.mapper.VariableMapper;
import feign.Feign;

import java.util.Collection;
import java.util.Optional;

class RequestSourceStrategy implements SignalRequestStrategy {

    AnswerConsumer remoteEntryPointAnswer;
    Collection<Long> loaderValIds;

    BlockVersionId sourceId;

    String currentServiceName;

    VariableMapper fromSourceMapper;

    RequestSourceStrategy(String sendServiceName, Collection<Long> loaderValIds, BlockVersionId sourceId,
                          String currentServiceName){
        remoteEntryPointAnswer = Feign.builder()
                .encoder(new SignalEncoder())
                .decoder(new AnswerDecoder())
                .target(AnswerConsumer.class, "http://" + sendServiceName + "/request");
        this.loaderValIds = loaderValIds;
        this.sourceId = sourceId;
        this.currentServiceName = currentServiceName;
    }


    public AnswerDto send(ActivityContext activityContext){
        VariablesContainer sourceContainer = createSourceContainer(activityContext);
        SignalDTO signalDTO = new SignalDTO(sourceId, sourceContainer,currentServiceName,
                activityContext.getCurrentBlockId(), SignalType.REQUEST_SOURCE);
        return remoteEntryPointAnswer.answer(signalDTO);
    }


    public VariablesContainer getAnswer(ActivityContext activityContext){
        return fromSourceMapper.map(activityContext.getRequestSignalVariables());
    }


    private VariablesContainer createSourceContainer(ActivityContext activityContext){
        VariablesContainer sourceContainer = VariableContainerFactory.getSimpleVariableContainer();
        for (Long loaderId : loaderValIds){
            Optional<ProcessValue> value = activityContext.getValueById(loaderId);

            if (value.isEmpty()) throw new NullPointerException("Context variable "
                    + activityContext.getPureVariable(loaderId).getName()
                    + " not ready for sending to source");
            sourceContainer.addVariable(value.get());
        }
        return sourceContainer;
    }
}
