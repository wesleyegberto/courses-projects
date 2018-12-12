package com.github.wesleyegberto.kafka.simpleconsumer.properties;

import java.util.Properties;

public class ProducerProperties {
    public static Properties createRequiredProperties() {
        Properties props = new Properties();

        // List of brokers
        props.put("bootstrap.servers", "localhost:9092");

        // Deserializers
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

        props.put("group.id", "simple-consumer");

        return props;
    }
}
