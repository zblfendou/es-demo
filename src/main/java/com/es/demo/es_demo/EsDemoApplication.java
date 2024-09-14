package com.es.demo.es_demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.KafkaListener;

@SpringBootApplication
public class EsDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(EsDemoApplication.class, args);
    }

    @KafkaListener(topics = "string_topic_1",groupId = "my-group")
    public void listen(String message) {
        System.out.println("Received message: " + message);
    }
}
