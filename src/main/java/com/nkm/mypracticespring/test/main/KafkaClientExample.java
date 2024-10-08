package com.nkm.mypracticespring.test.main;

import java.lang.System;
import java.io.*;
import java.util.*;
import java.time.*;
import java.util.concurrent.Executors;

import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.*;

public class KafkaClientExample {
    public static void main(String[] args) {
        try {
            String topic = "topic_java_spring_01";
            final Properties config = readConfig("client.properties");

            Executors.newSingleThreadExecutor().submit(() -> produce(topic, config));
            consume(topic, config, 1);
//            consume(topic, config, 2);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Properties readConfig(final String configFile) throws IOException {
        final Properties config = new Properties();
        try (InputStream inputStream = ClassLoader.getSystemResourceAsStream("client.properties")) {
            config.load(inputStream);
        }

        return config;
    }

    public static void produce(String topic, Properties config) {
        // sets the message serializers
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        config.put(ProducerConfig.CLIENT_ID_CONFIG, "manhnk");

        // creates a new producer instance and sends a sample message to the topic
        Producer<String, String> producer = new KafkaProducer<>(config);
        for (int i = 0; i < 1_999_999_999; i++) {
            String key = "key_" + i;
            String value = "value_" + i;
            producer.send(new ProducerRecord<>(topic, key, value));
        }
        producer.close();
    }

    public static void consume (String topic, Properties config, int consumeId) {
        // sets the group ID, offset and message deserializers
        config.put(ConsumerConfig.GROUP_ID_CONFIG, "java-group-1");
        config.put(ConsumerConfig.CLIENT_ID_CONFIG, "consume_manhnk");
        config.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());

        // creates a new consumer instance and subscribes to messages from the topic
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(config);
        consumer.subscribe(Collections.singletonList(topic));

        while (true) {
            // polls the consumer for new messages and prints them
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(500));
            for (ConsumerRecord<String, String> record : records) {
                System.out.printf(
                        "ConsumerId: %s - receive message from topic %s: key = %s value = %s%n", consumeId, topic, record.key(), record.value()
                );
            }
        }
    }
}