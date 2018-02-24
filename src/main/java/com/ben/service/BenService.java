package com.ben.service;

import com.ben.Foo;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
public class BenService {
    @Autowired
    private RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "fallback", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "5000")
    })
    public Foo getResponse() {
        log.info("Trying!");
        String response = restTemplate.getForObject("http://b:8000", String.class);
        return Foo.builder()
                .foo(String.format("Response from server: %s", response))
                .build();
    }

    public Foo fallback() {
        log.error("Problem!!");
        return Foo.builder()
                .foo("problem")
                .build();
    }
}
