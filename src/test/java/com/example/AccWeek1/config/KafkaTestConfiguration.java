package com.example.AccWeek1.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Profile;

@TestConfiguration
@Profile("test")
@EnableAutoConfiguration(exclude = {KafkaAutoConfiguration.class})
public class KafkaTestConfiguration {
    // This configuration completely disables Kafka in test environment
}