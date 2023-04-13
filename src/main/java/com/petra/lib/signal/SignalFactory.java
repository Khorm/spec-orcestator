package com.petra.lib.signal;

import com.petra.lib.signal.async.KafkaRequest;
import com.petra.lib.signal.async.KafkaResponse;
import com.petra.lib.signal.model.Version;
import com.petra.lib.signal.sync.SyncRequest;
import com.petra.lib.signal.sync.SyncResponse;
import com.petra.lib.variable.factory.VariableModel;
import com.petra.lib.variable.mapper.VariableMapper;
import com.petra.lib.variable.mapper.VariableMapperFactory;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public final class SignalFactory {

    public static RequestSignal createSyncRequestSignal(String URL, SignalObserver messageHandler,
                                                        Collection<VariableModel> requestSignalVariables,
                                                        Long signalId, Version version,
                                                        Long blockId) {
        VariableMapper variableMapper = VariableMapperFactory.createVariableMapper(requestSignalVariables);
        return new SyncRequest(URL, messageHandler, variableMapper, blockId, version, signalId);
    }


    public static ResponseSignal createSyncResponseSignal(String hostname, int port,
                                                          SignalListener handler, Long blockId, Version answerVersion,
                                                          Long signalId) {
//        VariableMapper answerMapper = VariableMapperFactory.createVariableMapper(answerSignalVariables);
        return new SyncResponse(hostname, port, handler, blockId, answerVersion, signalId);
    }

    public static RequestSignal createAsyncRequestSignal(String bootstrapServers, Long blockId,
                                                         Collection<VariableModel> requestSignalVariables,
                                                         Version requestVersion, Long signalId, SignalObserver messageHandler) {
        Producer<UUID, String> producer = createProducer(getProducerProps(bootstrapServers));
        Consumer<UUID, String> consumer = createConsumer(getConsumerProps(bootstrapServers, "test"));
        VariableMapper variableMapper = VariableMapperFactory.createVariableMapper(requestSignalVariables);

        return new KafkaRequest(producer, consumer, blockId, variableMapper, requestVersion, signalId, messageHandler);
    }

    public static ResponseSignal createAsyncResponseSignal(String bootstrapServers, SignalListener handler, Long blockId,
                                                           Version responseVersion,
                                                           Long signalId) {
        Producer<UUID, String> kafkaProducer = createProducer(getProducerProps(bootstrapServers));
        Consumer<UUID, String> kafkaConsumer = createConsumer(getConsumerProps(bootstrapServers, "test"));
//        VariableMapper variableMapper = VariableMapperFactory.createVariableMapper(responseSignalVariables);

        return new KafkaResponse(kafkaConsumer, kafkaProducer, handler, blockId, responseVersion, signalId);
    }

    private static Consumer<UUID, String> createConsumer(Map<String, Object> props) {
        DefaultKafkaConsumerFactory<UUID, String> kafkaConsumerFactory = new DefaultKafkaConsumerFactory<>(props);
        return kafkaConsumerFactory.createConsumer();
    }

    private static Producer<UUID, String> createProducer(Map<String, Object> props) {
        DefaultKafkaProducerFactory<UUID, String> kafkaProducerFactory = new DefaultKafkaProducerFactory<>(props);
        return kafkaProducerFactory.createProducer();
    }

    private static Map<String, Object> getConsumerProps(String bootstrapServers, String group) {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "group");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, 1);
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
        return props;
    }

    private static Map<String, Object> getProducerProps(String bootstrapServers) {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ProducerConfig.LINGER_MS_CONFIG, 10);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        return props;
    }
}
