package com.ben.service;

import com.ben.Person;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import lombok.extern.slf4j.Slf4j;
import net.spy.memcached.MemcachedClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@Slf4j
@Service
public class PersonService {
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private MemcachedClient memcachedClient;

    @HystrixCommand(fallbackMethod = "getFromCache", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "500")
    })
    public Person get(final String id) {
        log.info("Trying to get object from service");
        String response = restTemplate.getForObject("http://personservice:8000", String.class);

        Person person = new Person();
        person.setName(response);
        return person;
    }

    public Person getFromCache(final String id) {
        log.warn("Falling back");
        return (Person) memcachedClient.get(id);
    }

    public Person create(final Person person) {
        String key = UUID.randomUUID().toString();
        person.setId(key);
        memcachedClient.set(key, 1000, person);
        return person;
    }
}
