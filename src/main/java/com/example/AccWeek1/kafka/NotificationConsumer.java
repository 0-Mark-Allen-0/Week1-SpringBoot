package com.example.AccWeek1.kafka;

//This is a SERVICE for notification consumption

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class NotificationConsumer {
    @KafkaListener(topics = KafkaConstants.TOPIC, groupId = "notification-group")
    public void listen(ConsumerRecord<String, String> record) {
        System.out.println("Notification Received: " + record.value());
    }
}
