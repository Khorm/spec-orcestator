package com.petra.lib.XXXXXXsignal.___new.connector.consumer;

import com.petra.lib.block.VersionBlockId;
import com.petra.lib.context.variables.VariablesContext;
import com.petra.lib.XXXXXXsignal.___new.connector.producer.bridge.ConsumerEntryPoint;
import com.petra.lib.environment.dto.AnswerDto;
import com.petra.lib.environment.output.enums.SignalResult;
import com.petra.lib.environment.dto.ProducerSignalDto;
import com.petra.lib.environment.output.enums.RequestType;
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
    VersionBlockId blockId;
    Long signalId;
    String signalName;
    Version signalVersion;
    String producerServiceName;
    VersionBlockId consumerActionId;
    Long consumerServiceId;
    String consumerServiceName;
    ConsumerEntryPoint consumerEntryPoint;


    @Override
    public void send(VariablesContext sendingVariables, UUID businessId, ProducerHandler producerHandler) {
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

    private RequestType getRequestType() {
        return RequestType.SOURCE_REQUEST;
    }
}

