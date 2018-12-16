package com.github.wesleyegberto.kafka.simpleconsumer.string;

import com.github.wesleyegberto.kafka.simpleconsumer.properties.ProducerProperties;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

public class SubscriberConsumer {
    public static void main(String[] args) {
        KafkaConsumer consumer = new KafkaConsumer(ProducerProperties.createRequiredProperties());

        List<String> topics = Arrays.asList("my-topic");

        consumer.subscribe(topics);

        try {
            while(true) {
                ConsumerRecords<String, String> consumerRecords = consumer.poll(Duration.ofMillis(1000));
                consumerRecords.iterator()
                        .forEachRemaining(SubscriberConsumer::processRecord);
            }
        } catch (Exception ex) {
            System.err.println("Error: " + ex.getMessage());
        } finally {
            consumer.close();
        }
    }

    private static void processRecord(ConsumerRecord<String, String> record) {
        System.out.printf("Topic: %s, Partition: %d, Offset: %d, Key: %s, Value: %s%n",
              record.topic(), record.partition(), record.offset(), record.key(), record.value());
    }
}
