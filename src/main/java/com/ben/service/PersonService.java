package com.ben.service;

import com.ben.Person;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class PersonService {
    @Autowired
    private RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "fallback1", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "500")
    })
    public Person getPerson() {
        log.info("Calling B service");
        String response = restTemplate.getForObject("http://b:8000", String.class);
        log.info("Done with B service call");
        return Person.builder()
                .name(response)
                .build();
    }

    @HystrixCommand(fallbackMethod = "fallback2", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "500")
    })
    public Person fallback1() throws InterruptedException {
        TimeUnit.SECONDS.sleep(3);
        throw new RuntimeException("Won't hit this point");
    }

    public Person fallback2() {
        return Person.builder()
                .name("fallback2")
                .build();
    }
}
