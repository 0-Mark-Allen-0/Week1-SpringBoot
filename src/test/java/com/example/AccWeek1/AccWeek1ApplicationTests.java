package com.example.AccWeek1;

import com.example.AccWeek1.kafka.NotificationProducer;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledIfEnvironmentVariable;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

@SpringBootTest
@ActiveProfiles("test")
class AccWeek1ApplicationTests {
	@DisabledIfEnvironmentVariable(named = "CI", matches = "true")
	@Test
	void contextLoads() {
	}

}
