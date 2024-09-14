package com.es.demo.es_demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = EsDemoApplication.class)
public class KafkaTest {
    @Autowired
    private KafkaTemplate<String, String> stringKafkaTemplate;

    @Test
    public void stringKafkaTemplate() {
        stringKafkaTemplate.send("string_topic_1", "hello");
    }
}
