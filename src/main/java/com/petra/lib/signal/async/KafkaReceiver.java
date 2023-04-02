package com.petra.lib.signal.async;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.petra.lib.signal.ReceiverSignal;
import com.petra.lib.signal.SignalListener;
import com.petra.lib.signal.SignalType;
import com.petra.lib.signal.model.SignalTransferModel;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.time.Duration;
import java.util.UUID;
import java.util.function.BiConsumer;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class KafkaReceiver implements ReceiverSignal {

    Consumer<String, String> kafkaConsumer;
    Producer<UUID, String> kafkaProducer;
    Thread listenerThread;
    Long signalId;
    ObjectMapper objectMapper = new ObjectMapper();


    public KafkaReceiver(Consumer kafkaConsumer, Producer<UUID, String> kafkaProducer,
                         SignalListener messageHandler, Long signalId
                        ) {
        this.kafkaConsumer = kafkaConsumer;
        this.kafkaProducer = kafkaProducer;
//        this.messageHandler = messageHandler;
        this.signalId = signalId;

        listenerThread = new Thread(() -> {
            while (true) {
                //max.poll.records
                ConsumerRecords<UUID, String> records = kafkaConsumer.poll(Duration.ZERO);
                for (ConsumerRecord<UUID, String> record : records) {

                    String message = record.value();
                    try {
                        SignalTransferModel model = objectMapper.readValue(message, SignalTransferModel.class);
                        messageHandler.executeSignal(model);
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    @Override
    public Long getId() {
        return signalId;
    }

    @Override
    public void start() {
        listenerThread.start();
    }

    @Override
    public void setAnswer(SignalTransferModel answer) {
        kafkaConsumer.commitSync();

        ProducerRecord<UUID, String> record;
        try {
            record = new ProducerRecord<>(answer.getScenarioId().toString(),
                    objectMapper.writeValueAsString(answer));
        } catch (JsonProcessingException e) {
//            sendErrorHandler.accept(e, answer);
            return;
        }
        kafkaProducer.send(record, (metadata, exception) -> {
            if (exception != null) {
//                sendErrorHandler.accept(exception, answer);
            }
        });
    }

    @Override
    public void executionError(SignalTransferModel executingRequest) {
        kafkaConsumer.commitSync();

        ProducerRecord<UUID, String> record = null;
        SignalTransferModel errorSignalTransferModel = new SignalTransferModel(null,
                executingRequest.getVersion(),
                getId(),
                executingRequest.getScenarioId(),
                SignalType.ERROR);
        try {            
            record = new ProducerRecord<>(errorSignalTransferModel.getScenarioId().toString(),
                    objectMapper.writeValueAsString(errorSignalTransferModel));
        } catch (JsonProcessingException e) {

        }
        kafkaProducer.send(record, (metadata, exception) -> {
            if (exception != null) {
//                sendErrorHandler.accept(exception, errorSignalTransferModel);
            }
        });
    }
}
