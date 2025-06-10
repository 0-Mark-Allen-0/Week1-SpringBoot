package com.example.AccWeek1;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

//This is a SERVICE for notification production
@Service
public class NotificationProducer {
    private final KafkaTemplate<String, String> kafkaTemplate;

    public NotificationProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendNotif(String message) {
        kafkaTemplate.send(KafkaConstants.TOPIC, message);
        System.out.println("Notification Sent: " + message);
    }
}
