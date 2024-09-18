package com.es.demo.es_demo.controller;

import com.es.demo.es_demo.service.PersonService;
import com.es.demo.es_demo.util.HttpResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试用例
 */
@RestController
@RequestMapping("/test/")
public class TestController {
    private static final Logger log = LoggerFactory.getLogger(TestController.class);
    private final PersonService personService;

    public TestController(PersonService personService) {
        this.personService = personService;
    }

    @RequestMapping("get")
    public HttpResult get(@RequestParam("name") String name) {
        log.debug("name:{}", name);
        int i = 1/0;
        System.out.println("cccccccccccc");
        return HttpResult.asOk(personService.getPersonByName(name));
    }
}
