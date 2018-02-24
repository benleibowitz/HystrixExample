package com.ben.controller;

import com.ben.Person;
import com.ben.service.PersonService;
import com.netflix.hystrix.exception.HystrixRuntimeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/")
public class PersonController {
    @Autowired
    private PersonService service;

    @RequestMapping(method = RequestMethod.GET)
    public Person getPerson() {
        return service.getPerson();
    }
}
