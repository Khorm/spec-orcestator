package com.petra.lib.signal.factory;

import com.petra.lib.signal.model.SignalModel;
import com.petra.lib.signal.KafkaBridge;
import com.petra.lib.signal.ProducerSignal;
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

public final class SignalFactory {

     public static ProducerSignal createProducerSignal(SignalModel signalModel, String bootstrapServers){

          VariableMapper variableMapper = VariableMapperFactory.createVariableMapper(signalModel.getVariableModels());

          Consumer<String, String> kafkaConsumer = createConsumer(getConsumerProps(bootstrapServers, signalModel.getName()));
          Producer<String, String> kafkaProducer = createProducer(getProducerProps(bootstrapServers));


          KafkaBridge kafkaBridge = new KafkaBridge(kafkaConsumer, kafkaProducer, manager, )
     }


     private static Consumer<String, String> createConsumer(Map<String, Object> props){
          DefaultKafkaConsumerFactory<String, String> kafkaConsumerFactory = new DefaultKafkaConsumerFactory<>(props);
          return kafkaConsumerFactory.createConsumer();
     }
     private static Producer<String, String> createProducer(Map<String, Object> props){
          DefaultKafkaProducerFactory<String, String> kafkaProducerFactory = new DefaultKafkaProducerFactory<>(props);
          return kafkaProducerFactory.createProducer();
     }

     private static Map<String, Object> getConsumerProps(String bootstrapServers, String group){
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
