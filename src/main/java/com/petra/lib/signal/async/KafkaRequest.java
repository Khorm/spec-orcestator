package com.petra.lib.signal.async;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.petra.lib.signal.RequestSignal;
import com.petra.lib.signal.SignalObserver;
import com.petra.lib.signal.SignalType;
import com.petra.lib.signal.model.SignalTransferModel;
import com.petra.lib.signal.model.Version;
import com.petra.lib.variable.mapper.VariableMapper;
import com.petra.lib.variable.process.ProcessVariable;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.time.Duration;
import java.util.Collection;
import java.util.UUID;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class KafkaRequest implements RequestSignal {
    Producer<UUID, String> kafkaProducer;
    Consumer<UUID, String> kafkaConsumer;
    ObjectMapper objectMapper = new ObjectMapper();
    Long blockId;
    VariableMapper senderMapper;
    Version version;
    Long signalId;
    SignalObserver messageHandler;

    @Override
    public void send(Collection<ProcessVariable> senderVariables, UUID scenarioId) {
        ProducerRecord<UUID, String> record;
        Collection<ProcessVariable> signalVariables = senderMapper.map(senderVariables);
        SignalTransferModel signalTransferModel = new SignalTransferModel(
                signalVariables,
                version,
                signalId,
                scenarioId,
                blockId,
                SignalType.REQUEST);
        try {
            record = new ProducerRecord<>(scenarioId.toString(), objectMapper.writeValueAsString(signalTransferModel));
        } catch (JsonProcessingException e) {
            messageHandler.error(e, signalTransferModel);
            return;
        }
        kafkaProducer.send(record, (metadata, exception) -> {
            if (exception != null) {
                messageHandler.error(exception, signalTransferModel);
            }
        });
    }

    @Override
    public Long getId() {
        return signalId;
    }

    @Override
    public void startSignal() {
        Thread listenerThread = new Thread(() -> {
            while (true) {
                //max.poll.records
                ConsumerRecords<UUID, String> records = kafkaConsumer.poll(Duration.ZERO);
                for (ConsumerRecord<UUID, String> record : records) {

                    String message = record.value();
                    try {
                        SignalTransferModel model = objectMapper.readValue(message, SignalTransferModel.class);
                        messageHandler.executed(model);
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        listenerThread.start();
    }
}
