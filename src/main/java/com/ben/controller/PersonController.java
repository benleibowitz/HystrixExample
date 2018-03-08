package com.ben.controller;

import com.ben.Person;
import com.ben.service.PersonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@Slf4j
@RequestMapping("/")
public class PersonController {
    @Autowired
    private PersonService service;

    @RequestMapping(value = "person/{id}", method = RequestMethod.GET)
    public Person getPerson(@PathVariable("id") final String id) {
        return service.get(id);
    }

    @RequestMapping(value = "person", method = RequestMethod.POST)
    public Person createPerson(@Valid final Person person) {
        return service.create(person);
    }
}
