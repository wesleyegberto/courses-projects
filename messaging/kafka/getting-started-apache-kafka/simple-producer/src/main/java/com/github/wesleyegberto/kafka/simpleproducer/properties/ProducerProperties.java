package com.github.wesleyegberto.kafka.simpleproducer.properties;

import java.util.Properties;

public class ProducerProperties {
    public static Properties createRequiredProperties() {
        Properties props = new Properties();

        // List of brokers
        props.put("bootstrap.servers", "localhost:9092");

        // Serializers
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        return props;
    }
}
