package com.petra.lib.signal.response.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.petra.lib.PetraProps;
import com.petra.lib.manager.block.BlockId;
import com.petra.lib.manager.block.ProcessVariableDto;
import com.petra.lib.signal.model.Version;
import com.petra.lib.signal.response.controller.SignalResponseHandler;
import com.petra.lib.signal.SignalId;
import com.petra.lib.signal.dto.RequestDto;
import com.petra.lib.signal.dto.ResponseDto;
import com.petra.lib.signal.model.SignalModel;
import com.petra.lib.signal.response.ResponseSignal;
import com.petra.lib.signal.response.ResponseType;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.kafka.common.serialization.UUIDDeserializer;
import org.apache.kafka.common.serialization.UUIDSerializer;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;

import java.time.Duration;
import java.util.*;

/**
 * Сигнал, получащий данные и отвечающий на них
 */
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Log4j2
public class KafkaResponse implements ResponseSignal {

    /**
     * Каfka consumer and producer for this signal
     */
    Consumer<UUID, String> kafkaConsumer;
    Producer<UUID, String> kafkaProducer;

    /**
     * Listener thread for kafka messages
     */
    Thread listenerThread;

    /**
     * Signal id, which listened by this listener
     */
    SignalId signalId;
    Version version;
    ObjectMapper objectMapper = new ObjectMapper();
    /**
     * Listening signal name
     */
    String topic;

    /**
     * Subscribed handler who handles this request.
     */
    SignalResponseHandler signalListener;


    public KafkaResponse(SignalModel signalModel, PetraProps petraProps, SignalResponseHandler signalListener) {
        this.signalId = new SignalId(signalModel.getSignalId(), signalModel.getVersion().getMajor());
        this.version = signalModel.getVersion();
        this.topic = signalModel.getSignalName() + "/" + signalModel.getVersion().getVersionName();
        this.signalListener = signalListener;

        String bootstrapServers = (String) petraProps.getProp("petra", "signal", "bootstrapServers");
        Map<String, Object> consumerUserParams = petraProps.getPropsMap("petra", "signal", signalModel.getSignalName(),
                signalModel.getVersion().getVersionName(), "consumer");
        this.kafkaConsumer = new DefaultKafkaConsumerFactory<UUID, String>(getConsumerProps(bootstrapServers,
                signalModel.getServiceName(),
                consumerUserParams))
                .createConsumer();

        Map<String, Object> producerUserParams = petraProps.getPropsMap("petra", "signal", signalModel.getSignalName(),
                signalModel.getVersion().getVersionName(), "producer");
        this.kafkaProducer = new DefaultKafkaProducerFactory<UUID, String>(getProducerProps(bootstrapServers, producerUserParams))
                .createProducer();

        listenerThread = new Thread(() -> {
            kafkaConsumer.subscribe(Collections.singletonList(topic));
            while (true) {
                ConsumerRecords<UUID, String> records = kafkaConsumer.poll(Duration.ZERO);
                for (ConsumerRecord<UUID, String> record : records) {

                    String message = record.value();
                    try {
                        log.debug("SIGNAL GET DATA {}", message);
                        RequestDto requestDto = objectMapper.readValue(message, RequestDto.class);
                        this.signalListener.handleSignal(requestDto, this);
                    } catch (JsonProcessingException e) {
                        //TODO обработать ошибку в setError
                    }
                }
            }
        });

    }


    @Override
    public SignalId getId() {
        return signalId;
    }

    @Override
    public void startSignal() {
        listenerThread.start();
    }

    @Override
    public void setAnswer(Collection<ProcessVariableDto> signalAnswerVariables, BlockId requestBlockId, UUID scenarioId, BlockId responseBlockId) {
        kafkaConsumer.commitSync();
        ProducerRecord<UUID, String> record;
        try {
            ResponseDto answer = new ResponseDto(
                    scenarioId,
                    signalId,
                    requestBlockId,
                    responseBlockId,
                    ResponseType.RESPONSE,
                    signalAnswerVariables
            );

            record = new ProducerRecord<>(topic + "_answer", answer.getScenarioId(),
                    objectMapper.writeValueAsString(answer));
        } catch (JsonProcessingException e) {
            log.error(e);
            return;
        }

        //send answer in answer topic
        kafkaProducer.send(record, (metadata, exception) -> {
            if (exception != null) {
                log.error(exception);
            }
        });
    }

    @Override
    public void setError(BlockId requestBlockId, UUID scenarioId, BlockId responseBlockId) {
        kafkaConsumer.commitSync();

        ProducerRecord<UUID, String> record = null;
        ResponseDto answer = new ResponseDto(
                scenarioId,
                signalId,
                requestBlockId,
                responseBlockId,
                ResponseType.ERROR,
                null
        );
        try {
            record = new ProducerRecord<>(topic + "_answer", scenarioId,
                    objectMapper.writeValueAsString(answer));
        } catch (JsonProcessingException e) {
            log.error(e);
        }

        //send kafka error to answer topic
        kafkaProducer.send(record, (metadata, exception) -> {
            if (exception != null) {
                log.error(exception);
            }
        });
    }

    private static Map<String, Object> getConsumerProps(String bootstrapServers, String groupName, Map<String, Object> userParams) {
        Map<String, Object> props = new HashMap<>();
        props.putAll(userParams);
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, groupName);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, UUIDDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
        return props;
    }

    private static Map<String, Object> getProducerProps(String bootstrapServers, Map<String, Object> userParams) {
        Map<String, Object> props = new HashMap<>();
        props.putAll(userParams);
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, UUIDSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        return props;
    }
}
