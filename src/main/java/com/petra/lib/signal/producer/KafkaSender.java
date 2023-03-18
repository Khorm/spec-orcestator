package com.petra.lib.signal.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.petra.lib.signal.model.ExecutionRequest;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.function.BiConsumer;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class KafkaSender implements Sender {
    Producer<String, String> kafkaProducer;

    @Override
    public void send(ExecutionRequest executionRequest, BiConsumer<Exception, ExecutionRequest> sendErrorHandler) {
        ProducerRecord<String, String> record;
        try {
            record = new ProducerRecord<>(executionRequest.getScenarioId().toString(),
                    executionRequest.toJson());
        } catch (JsonProcessingException e) {
            sendErrorHandler.accept(e, executionRequest);
            return;
        }
        kafkaProducer.send(record, (metadata, exception) -> {
            if (exception != null){
                sendErrorHandler.accept(exception, executionRequest);
            }
        });
    }

}
