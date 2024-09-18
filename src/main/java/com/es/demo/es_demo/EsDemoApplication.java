package com.es.demo.es_demo;

import com.es.demo.es_demo.document.LogEntry;
import com.es.demo.es_demo.service.LogEntryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.KafkaListener;

import java.time.LocalDateTime;

@SpringBootApplication
public class EsDemoApplication {
    private static final Logger logger = LoggerFactory.getLogger(EsDemoApplication.class);
    private final LogEntryService logEntryService;

    public EsDemoApplication(LogEntryService logEntryService) {
        this.logEntryService = logEntryService;
    }

    public static void main(String[] args) {
        SpringApplication.run(EsDemoApplication.class, args);
    }

    @KafkaListener(topics = "logs", groupId = "log-group")
    public void listen(String message) {
        logger.info("Received message: {}", message);
        logEntryService.saveLog(new LogEntry.Builder().message(message).timestamp(LocalDateTime.now()).build());
    }
}
