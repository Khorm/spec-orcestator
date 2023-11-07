package com.petra.lib.XXXXXXsignal.___new.connector.consumer;

import com.petra.lib.block.VersionId;
import com.petra.lib.context.variables.VariablesContainerImpl;
import com.petra.lib.XXXXXXsignal.___new.connector.producer.bridge.ConsumerEntryPoint;
import com.petra.lib.remote.signal.AnswerDto;
import com.petra.lib.remote.input.SignalResult;
import com.petra.lib.remote.signal.ProducerSignalDto;
import com.petra.lib.remote.signal.SignalType;
import com.petra.lib.XXXXXXsignal.___new.connector.producer.bridge.ProducerHandler;
import com.petra.lib.XXXXXXsignal.model.Version;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

/**
 * Коннектор для отправки сообщения напрямую из места отправки через http
 */
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ComsumerImpl implements Consumer {
    VersionId blockId;
    Long signalId;
    String signalName;
    Version signalVersion;
    String producerServiceName;
    VersionId consumerActionId;
    Long consumerServiceId;
    String consumerServiceName;
    ConsumerEntryPoint consumerEntryPoint;


    @Override
    public void send(VariablesContainerImpl sendingVariables, UUID businessId, ProducerHandler producerHandler) {
        ProducerSignalDto producerSignalDto = new ProducerSignalDto(
                businessId,
                sendingVariables.getJSONVariablesList(),

                signalId,
                signalVersion,
                signalName,

                producerServiceName,
                blockId,

                consumerActionId,
                consumerServiceId,
                consumerServiceName,
                getRequestType());

        AnswerDto answerDto = consumerEntryPoint.getAnswer(producerSignalDto);
        if (answerDto.getSignalResult() == SignalResult.ERROR){
            producerHandler.error();
        }
    }

    private SignalType getRequestType() {
        return SignalType.SOURCE_REQUEST;
    }
}

