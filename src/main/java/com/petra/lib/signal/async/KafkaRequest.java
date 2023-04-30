package com.petra.lib.signal.async;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.petra.lib.signal.RequestSignal;
import com.petra.lib.signal.SignalRequestListener;
import com.petra.lib.signal.SignalType;
import com.petra.lib.signal.model.SignalTransferModel;
import com.petra.lib.signal.model.Version;
import com.petra.lib.variable.mapper.VariableMapper;
import com.petra.lib.manager.block.ProcessVariableDto;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.time.Duration;
import java.util.Collection;
import java.util.UUID;

/**
 * Kafka listener signal, who handle requests
 */
@Log4j2
@FieldDefaults(level = AccessLevel.PRIVATE)
public class KafkaRequest implements RequestSignal {
    Producer<UUID, String> kafkaProducer;
    Consumer<UUID, String> kafkaConsumer;
    ObjectMapper objectMapper = new ObjectMapper();
    Long blockId;
    VariableMapper senderMapper;
    Version version;
    Long signalId;
    SignalRequestListener observer;
    String name;

    KafkaRequest(Producer<UUID, String> kafkaProducer, Consumer<UUID, String> kafkaConsumer, Long blockId,
                 VariableMapper senderMapper,  Version version, Long signalId,  String name){
        this.kafkaProducer = kafkaProducer;
        this.kafkaConsumer = kafkaConsumer;
        this.blockId = blockId;
        this.senderMapper = senderMapper;
        this.version = version;
        this.signalId = signalId;
        this.name = name;
    }

    @Override
    public void send(Collection<ProcessVariableDto> senderVariables, UUID scenarioId) {
        ProducerRecord<UUID, String> record;
        Collection<ProcessVariableDto> signalVariables = senderMapper.map(senderVariables);
        SignalTransferModel signalTransferModel = new SignalTransferModel(
                scenarioId,
                signalId,
                version,
                blockId,
                SignalType.REQUEST,
                signalVariables);
        try {
            record = new ProducerRecord<>(scenarioId.toString(), objectMapper.writeValueAsString(signalTransferModel));
        } catch (JsonProcessingException e) {
            observer.error(e, signalTransferModel);
            return;
        }
        kafkaProducer.send(record, (metadata, exception) -> {
            if (exception != null) {
                observer.error(exception, signalTransferModel);
            }
        });
    }

    @Override
    public void setObserver(SignalRequestListener signalRequestListener) {
        this.observer = observer;
    }

    @Override
    public Long getId() {
        return signalId;
    }

    @Override
    public void startSignal() {
        Thread listenerThread = new Thread(() -> {
            log.info("Signal {} started", name);
            while (true) {
                //max.poll.records
                ConsumerRecords<UUID, String> records = kafkaConsumer.poll(Duration.ZERO);
                for (ConsumerRecord<UUID, String> record : records) {

                    String message = record.value();
                    try {
                        SignalTransferModel model = objectMapper.readValue(message, SignalTransferModel.class);
                        observer.executed(model);
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        listenerThread.start();
    }
}
