package com.petra.lib.signal;

import com.petra.lib.signal.consumer.KafkaReceiver;
import com.petra.lib.signal.model.SignalTransferModel;
import com.petra.lib.signal.producer.KafkaSender;
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

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public final class SignalFactory {

    public static Signal createSignal(SignalModel signalModel, SignalObserver signalObserver) {
        KafkaReceiver kafkaReceiver = new KafkaReceiver(createConsumer(getConsumerProps(null, null)));
        KafkaSender kafkaSender = new KafkaSender(createProducer(getProducerProps(null)));
        VariableMapper variableMapper = VariableMapperFactory.createVariableMapper(signalModel.getVariableModelCollection());

        SignalImpl signal = new SignalImpl(kafkaReceiver, kafkaSender, signalModel.getId(), signalModel.getVersion(),
                variableMapper,signalObserver);
        return signal;
    }

//    public static Signal createListeningSignalSignal(SignalModel signalModel, java.util.function.Consumer<SignalTransferModel> executionHandler) {
//        KafkaReceiver kafkaReceiver = new KafkaReceiver(createConsumer(getConsumerProps(null, null)));
//        KafkaSender kafkaSender = new KafkaSender(createProducer(getProducerProps(null)));
//        VariableMapper variableMapper = VariableMapperFactory.createVariableMapper(signalModel.getVariableModelCollection());
//
//        SignalImpl signal = new SignalImpl(kafkaReceiver, kafkaSender, signalModel.getId(), signalModel.getVersion(),
//                variableMapper, executionHandler);
//        return signal;
//    }


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
