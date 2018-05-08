package com.ben.controller;

import com.ben.Person;
import com.ben.service.PersonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Person controller
 */
@RestController
@Slf4j
@RequestMapping("/")
public class PersonController {
    /**
     * Person service
     */
    @Autowired
    private PersonService service;

    /**
     * Get {@link Person} with ID. Certain IDs will trigger certain simulated scenarios
     * in the downstream service example.
     *
     * @param id of the Person to get. Will trigger scenarios based on ID.
     * @return {@link Person}
     */
    @RequestMapping(value = "persons/{id}", method = RequestMethod.GET)
    public Person getPerson(@PathVariable("id") final String id) {
        return service.get(id);
    }
}
