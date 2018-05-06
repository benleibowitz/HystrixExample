package com.ben.controller;

import com.ben.DownstreamServiceException;
import com.ben.Person;
import com.ben.service.PersonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/")
public class PersonController {
    @Autowired
    private PersonService service;

    @RequestMapping(value = "persons/{id}", method = RequestMethod.GET)
    public Person getPerson(@PathVariable("id") final String id,
                            @RequestParam(value = "should_timeout", required = false) final boolean shouldTimeout) throws DownstreamServiceException {
        return service.get(id, shouldTimeout);
    }
}
