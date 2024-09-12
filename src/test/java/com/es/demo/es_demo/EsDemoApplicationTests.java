package com.es.demo.es_demo;

import cn.hutool.json.JSONUtil;
import com.es.demo.es_demo.document.Person;
import com.es.demo.es_demo.service.ElasticsearchService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = EsDemoApplication.class)
class EsDemoApplicationTests {

    @Autowired
    private ElasticsearchService service;
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() throws IOException {
        List<Person> list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            Person person = new Person();
            person.setName("test_name_" + i);
            person.setAge(i);
            list.add(person);
        }
//        service.savePerson(list);
    }

    @Test
    void testPerson() {
        Person person = new Person();
        person.setName("test_name_1");
        person.setAge(1);
        String savePersonId = service.savePerson(person);
        Person getPerson = service.getPerson(savePersonId);
        assertEquals("test_name_1", getPerson.getName());
        assertTrue(service.exists(savePersonId));
        service.deletePerson(getPerson);
        assertFalse(service.exists(savePersonId));
    }

    @Test
    public void getAllPerson() {
        long size = service.personSize();
        System.out.println("size:" + size);
    }

    @Test
    public void delAllPerson() {
        List<String> ids = service.getAllPerson().stream().map(Person::getId).collect(Collectors.toList());
        service.delAll(ids);
        assertEquals(0, service.personSize());
    }

    @Test
    public void getSumAge() {
        System.out.println(service.getSumAge());
    }

    @Test
    public void getPersonByName() {
        prettyJson(service.getPersonByName("test_name_9"));
    }

    @Test
    public void updatePerson() {
        Person person = service.getPersonByName("test_name_9");
        person.setAge(9999);
        service.updatePerson(person);
        prettyJson(service.getPersonByName("test_name_9"));
    }


    private void prettyJson(Object obj) {
        try {
            System.out.println(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(objectMapper.readTree(JSONUtil.toJsonStr(obj))));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
