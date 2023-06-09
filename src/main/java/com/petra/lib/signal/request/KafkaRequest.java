package com.petra.lib.signal.request;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.petra.lib.PetraProps;
import com.petra.lib.manager.block.ProcessVariableDto;
import com.petra.lib.signal.SignalId;
import com.petra.lib.signal.dto.RequestDto;
import com.petra.lib.signal.dto.ResponseDto;
import com.petra.lib.signal.model.SignalModel;
import com.petra.lib.signal.model.Version;
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
 * Kafka signal listener, which handles requests
 */
@Log4j2
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class KafkaRequest implements RequestSignal {

    /**
     * Producer produces request to the signal request topic.
     * Request topics names as signal name.
     */
    Producer<UUID, String> kafkaProducer;

    /**
     * Consumer listens request answers from request.
     * Consumer topic names as signal name + '_answer'.
     */
    Consumer<UUID, String> kafkaConsumer;
    ObjectMapper objectMapper = new ObjectMapper();

    /**
     * ID of signal, which this signal model handles.
     */
    SignalId signalId;

    /**
     * Listener, which send the request and waits an answer
     */
    SignalRequestListener listener;

    /**
     * Name of signal, which this signal model handles.
     */
    String topic;


    public KafkaRequest(SignalModel signalModel,
                        PetraProps petraProps,
                        SignalRequestListener listener) {
        this.signalId = new SignalId(signalModel.getSignalId(), signalModel.getVersion());
        this.topic = signalModel.getSignalName() + "/" + signalId.getVersion().getVersionName();
        this.listener = listener;

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

    }

    @Override
    public void request(Collection<ProcessVariableDto> signalVariables, UUID scenarioId, Long requestBlockId, Version blockVersion) {
        ProducerRecord<UUID, String> record;
        RequestDto requestDto = new RequestDto(
                scenarioId,
                requestBlockId,
                blockVersion,
                signalVariables
        );
        try {
            record = new ProducerRecord<UUID, String>(topic,
                    scenarioId, objectMapper.writeValueAsString(requestDto));
        } catch (JsonProcessingException e) {
            listener.error(e, requestDto, signalId);
            return;
        }

        kafkaProducer.send(record, (metadata, exception) -> {
            if (exception != null) {
                listener.error(exception, requestDto, signalId);
            }
        });
    }

    @Override
    public SignalId getId() {
        return signalId;
    }

    @Override
    public void startSignal() {
        Thread listenerThread = new Thread(() -> {
            log.info("Signal {} started", topic);
            kafkaConsumer.subscribe(Collections.singletonList(topic + "_answer"));
            while (true) {
                ConsumerRecords<UUID, String> records = kafkaConsumer.poll(Duration.ZERO);
                for (ConsumerRecord<UUID, String> record : records) {

                    String message = record.value();
                    try {
                        ResponseDto responseDto = objectMapper.readValue(message, ResponseDto.class);
                        listener.executed(responseDto, signalId);
                    } catch (JsonProcessingException e) {
                        log.error(message, e);
                    }
                }
            }
        });
        listenerThread.start();
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
