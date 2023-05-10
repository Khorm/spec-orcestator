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
import org.apache.kafka.common.serialization.UUIDDeserializer;
import org.apache.kafka.common.serialization.UUIDSerializer;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public final class SignalFactory {

    public static RequestSignal createSyncRequestSignal(SignalModel signal, Long blockId) {
        VariableMapper variableMapper = VariableMapperFactory.createVariableMapper(signal.getRequestVariableCollection());
        return new SyncRequest(signal.getServiceName() + signal.getPath(),  variableMapper, blockId,
                signal.getVersion(), signal.getId());
    }


    public static ResponseSignal createSyncResponseSignal(SignalModel signal, Long blockId,
                                                          ConfigurableListableBeanFactory beanFactory) {
//        VariableMapper answerMapper = VariableMapperFactory.createVariableMapper(answerSignalVariables);
        return new SyncResponse(signal.getPath(), signal.getName(), blockId, signal.getVersion(), signal.getId(), beanFactory);
    }

    public static RequestSignal createAsyncRequestSignal(SignalModel signal, String bootstrapServers, Long blockId) {
//        Producer<UUID, String> producer = createProducer(getProducerProps(bootstrapServers));
//        Consumer<UUID, String> consumer = createConsumer(getConsumerProps(bootstrapServers, signal.getPath()));
//        VariableMapper variableMapper = VariableMapperFactory.createVariableMapper(signal.getRequestVariableCollection());
//
//        return new KafkaRequest(producer, consumer, blockId, variableMapper, signal.getVersion(),
//                signal.getId(), null, signal.getName());
        return null;
    }

    public static ResponseSignal createAsyncResponseSignal(SignalModel signal, String bootstrapServers, Long blockId) {
        Producer<UUID, String> kafkaProducer = createProducer(getProducerProps(bootstrapServers));
        Consumer<UUID, String> kafkaConsumer = createConsumer(getConsumerProps(bootstrapServers));

        return new KafkaResponse(kafkaConsumer, kafkaProducer, blockId, signal.getVersion(), signal.getId(), signal.getPath());
    }

    private static Consumer<UUID, String> createConsumer(Map<String, Object> props) {
        DefaultKafkaConsumerFactory<UUID, String> kafkaConsumerFactory = new DefaultKafkaConsumerFactory<>(props);
        return kafkaConsumerFactory.createConsumer();
    }

    private static Producer<UUID, String> createProducer(Map<String, Object> props) {
        DefaultKafkaProducerFactory<UUID, String> kafkaProducerFactory = new DefaultKafkaProducerFactory<>(props);
        return kafkaProducerFactory.createProducer();
    }

    private static Map<String, Object> getConsumerProps(String bootstrapServers) {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "petra");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, UUIDDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, 1);
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
        return props;
    }

    private static Map<String, Object> getProducerProps(String bootstrapServers) {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ProducerConfig.LINGER_MS_CONFIG, 10);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, UUIDSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        return props;
    }
}
