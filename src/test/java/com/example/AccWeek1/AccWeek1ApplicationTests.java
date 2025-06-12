package com.example.AccWeek1;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class AccWeek1ApplicationTests {

	@Test
	void contextLoads() {
		// This test verifies that the Spring context loads successfully
		// Kafka is disabled via kafka.enabled=false property
	}
}