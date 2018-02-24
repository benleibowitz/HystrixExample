package com.ben.service;

import com.ben.Person;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;

@Slf4j
@Service
public class PersonService {
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private Producer<String, String> producer;

    @HystrixCommand(fallbackMethod = "sendMessage", commandProperties = {
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

    @HystrixCommand(fallbackMethod = "fallback", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "500")
    })
    public Person sendMessage() throws InterruptedException {
        producer.send(new ProducerRecord<>("ben-topic", String.valueOf(Instant.now().toEpochMilli()), "Unable to publish person"));
        return Person.builder()
                .build();
    }

    public Person fallback() {
        return Person.builder()
                .name("fallback2")
                .build();
    }
}
