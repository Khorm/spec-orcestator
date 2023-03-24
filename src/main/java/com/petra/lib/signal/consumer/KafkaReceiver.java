package com.petra.lib.signal.consumer;

import com.petra.lib.signal.model.ExecutionResponse;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;

import java.time.Duration;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class KafkaReceiver implements Receiver {

    final Consumer<String, String> kafkaConsumer;
    final Thread listenerThread;
    java.util.function.Consumer<String> messageHandler;

    public KafkaReceiver(Consumer kafkaConsumer) {
        this.kafkaConsumer = kafkaConsumer;

        listenerThread = new Thread(() -> {
            while (true) {
                //max.poll.records
                ConsumerRecords<String, String> records = kafkaConsumer.poll(Duration.ZERO);
                for (ConsumerRecord<String, String> record : records) {
                    String message = record.value();
                    messageHandler.accept(message);
                }
            }
        });
    }

    @Override
    public synchronized void start() {
        listenerThread.start();
    }

    @Override
    public void accept() {
        kafkaConsumer.commitSync();
    }
}
