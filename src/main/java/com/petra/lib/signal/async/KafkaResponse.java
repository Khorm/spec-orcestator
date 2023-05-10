package com.petra.lib.signal.async;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.petra.lib.manager.thread.ThreadManager;
import com.petra.lib.signal.ResponseSignal;
import com.petra.lib.signal.SignalResponseListener;
import com.petra.lib.signal.SignalType;
import com.petra.lib.signal.model.SignalTransferModel;
import com.petra.lib.signal.model.Version;
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
import java.util.Collections;
import java.util.UUID;

/**
 * Сигнал, получащий данные и отвечающий на них
 */
@FieldDefaults(level = AccessLevel.PRIVATE)
@Log4j2
public class KafkaResponse implements ResponseSignal {

    final Consumer<String, String> kafkaConsumer;
    final Producer<UUID, String> kafkaProducer;
    final Thread listenerThread;
    final Long signalId;
    final Long blockId;
    final ObjectMapper objectMapper = new ObjectMapper();
    final Version answerVersion;
    SignalResponseListener signalResponseListener;
    final String topic;


    public KafkaResponse(Consumer kafkaConsumer, Producer<UUID, String> kafkaProducer,
                         Long blockId,
                         Version answerVersion,
                         Long signalId, String topic) {

        this.kafkaConsumer = kafkaConsumer;
        this.kafkaProducer = kafkaProducer;
        this.signalId = signalId;
        this.blockId = blockId;
        this.answerVersion = answerVersion;
        this.topic = topic;

        listenerThread = new Thread(() -> {
            kafkaConsumer.subscribe(Collections.singletonList(topic));
            while (true) {
                //max.poll.records
                ConsumerRecords<UUID, String> records = kafkaConsumer.poll(Duration.ZERO);
                for (ConsumerRecord<UUID, String> record : records) {

                    String message = record.value();
                    try {
                        SignalTransferModel model = objectMapper.readValue(message, SignalTransferModel.class);
                        log.debug("SIGNAL GET DATA {}", model.toString());
                        ThreadManager.execute(() -> signalResponseListener.executeSignal(model));
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
    public void startSignal() {
        listenerThread.start();
    }

    @Override
    public synchronized void setAnswer(Collection<ProcessVariableDto> contextVariables, UUID scenarioId) {
        kafkaConsumer.commitSync();
        ProducerRecord<UUID, String> record;
        try {
            SignalTransferModel answer = new SignalTransferModel(
                    scenarioId,
                    signalId,
                    answerVersion,
                    blockId,
                    SignalType.RESPONSE,
                    contextVariables);

            record = new ProducerRecord<>(topic+"_answer", answer.getScenarioId(),
                    objectMapper.writeValueAsString(answer));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
//            sendErrorHandler.accept(e, answer);
            return;
        }
        kafkaProducer.send(record, (metadata, exception) -> {
            if (exception != null) {
                exception.printStackTrace();
//                sendErrorHandler.accept(exception, answer);
            }
        });
    }

    @Override
    public void setError(UUID scenarioId) {
        kafkaConsumer.commitSync();

        ProducerRecord<UUID, String> record = null;
        SignalTransferModel errorSignalTransferModel = new SignalTransferModel(
                scenarioId,
                signalId,
                answerVersion,
                blockId,
                SignalType.ERROR,
                null
        );
        try {
            record = new ProducerRecord<>(topic+"_answer", errorSignalTransferModel.getScenarioId(),
                    objectMapper.writeValueAsString(errorSignalTransferModel));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        kafkaProducer.send(record, (metadata, exception) -> {
            if (exception != null) {
                exception.printStackTrace();
//                sendErrorHandler.accept(exception, errorSignalTransferModel);
            }
        });
    }

    @Override
    public void setListener(SignalResponseListener signalResponseListener) {
        this.signalResponseListener = signalResponseListener;
    }
}
