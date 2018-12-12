package com.github.wesleyegberto.kafka.simpleproducer.string;

import com.github.wesleyegberto.kafka.simpleproducer.properties.ProducerProperties;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.KafkaException;

import java.io.IOException;

public class SimpleStringRecordProducer {
    public static void main(String[] args) {
        KafkaProducer producer = new KafkaProducer(ProducerProperties.createRequiredProperties());

        // Create a record to send with a topic, key and value defined
        // The value must match the type for serializer

        for (int i = 0; i < 100; i++) {
            sendRecord(producer, new ProducerRecord("my-topic", "simple-producer", "This is a message produced from Java " + i));
        }

        producer.close();
    }

    private static void sendRecord(KafkaProducer producer, ProducerRecord stringRecord) {
        try {
            producer.send(stringRecord);
        } catch (KafkaException ex) {
            System.err.println("Error: " + ex.getMessage());
        }
    }
}
