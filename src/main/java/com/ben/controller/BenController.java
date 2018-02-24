package com.ben.controller;

import com.ben.Foo;
import com.ben.service.BenService;
import com.netflix.hystrix.exception.HystrixRuntimeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/")
public class BenController {
    @Autowired
    private BenService service;

    @RequestMapping(method = RequestMethod.GET)
    public Foo doStuff() {
        try {
            return service.getResponse();
        } catch (HystrixRuntimeException e) {
            log.error("Hystrix error: " + e.getMessage());
            return Foo.builder()
                    .foo(e.getMessage())
                    .build();
        }
    }
}
