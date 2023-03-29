package com.petra.lib.signal.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.petra.lib.signal.model.SignalTransferModel;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.UUID;
import java.util.function.BiConsumer;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class KafkaSender implements Sender {
    Producer<UUID, String> kafkaProducer;
    ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void send(SignalTransferModel executionRequest, BiConsumer<Exception, SignalTransferModel> sendErrorHandler) {
        ProducerRecord<UUID, String> record;
        try {
            record = new ProducerRecord<>(executionRequest.getScenarioId().toString(),
                    objectMapper.writeValueAsString(executionRequest));
        } catch (JsonProcessingException e) {
            sendErrorHandler.accept(e, executionRequest);
            return;
        }
        kafkaProducer.send(record, (metadata, exception) -> {
            if (exception != null) {
                sendErrorHandler.accept(exception, executionRequest);
            }
        });
    }

}
