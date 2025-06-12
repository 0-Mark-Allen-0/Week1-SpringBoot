package com.example.AccWeek1;

import com.example.AccWeek1.kafka.NotificationProducer;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledIfEnvironmentVariable;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
@SpringBootTest
@ActiveProfiles("test")
class AccWeek1ApplicationTests {

	// Mock Kafka components to prevent connection attempts
	@MockitoBean
	private NotificationProducer notificationProducer;

	@MockitoBean
	private KafkaTemplate<String, String> kafkaTemplate;

	@Test
	void contextLoads() {
		// This test verifies that the Spring context loads successfully
		// Kafka components are mocked, so no actual Kafka connection is made
	}
}