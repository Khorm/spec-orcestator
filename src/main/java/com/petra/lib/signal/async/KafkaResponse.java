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
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.time.Duration;
import java.util.Collection;
import java.util.UUID;

/**
 * Сигнал, получащий данные и отвечающий на них
 */
@FieldDefaults(level = AccessLevel.PRIVATE)
public class KafkaResponse implements ResponseSignal {

    Consumer<String, String> kafkaConsumer;
    Producer<UUID, String> kafkaProducer;
    Thread listenerThread;
    Long signalId;
    Long blockId;
    ObjectMapper objectMapper = new ObjectMapper();
    Version answerVersion;
    SignalResponseListener signalResponseListener;


    public KafkaResponse(Consumer kafkaConsumer, Producer<UUID, String> kafkaProducer,
                         Long blockId,
                         Version answerVersion,
                         Long signalId) {

        this.kafkaConsumer = kafkaConsumer;
        this.kafkaProducer = kafkaProducer;
        this.signalId = signalId;
        this.blockId = blockId;
        this.answerVersion = answerVersion;

        listenerThread = new Thread(() -> {
            while (true) {
                //max.poll.records
                ConsumerRecords<UUID, String> records = kafkaConsumer.poll(Duration.ZERO);
                for (ConsumerRecord<UUID, String> record : records) {

                    String message = record.value();
                    try {
                        SignalTransferModel model = objectMapper.readValue(message, SignalTransferModel.class);
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
    public void setAnswer(Collection<ProcessVariableDto> contextVariables, UUID scenarioId) {
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

            record = new ProducerRecord<>(answer.getScenarioId().toString(),
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
            record = new ProducerRecord<>(errorSignalTransferModel.getScenarioId().toString(),
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
