package com.ben.service;

import com.ben.Person;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class PersonService {
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private JmsTemplate jmsTemplate;

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
        jmsTemplate.send(new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                return session.createTextMessage("Unable to create person ts=" + System.currentTimeMillis());
            }
        });
        return Person.builder()
                .build();
    }

    public Person fallback() {
        throw new IllegalStateException("Fallback"); // database
    }
}
