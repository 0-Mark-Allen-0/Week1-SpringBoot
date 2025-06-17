package com.example.AccWeek1.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration // Marks this as a source of Spring beans
public class AppConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate(); // Now this RestTemplate can be autowired anywhere
    }
}
