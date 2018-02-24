package com.ben.config;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.util.Properties;

@Configuration
public class Config {
    @Bean
    public Producer producer() {
        Properties props = new Properties();
        props.put("bootstrap.servers", "http://messagebroker:9092");
        props.put("acks", "all");
        props.put("retries", 0);
        props.put("batch.size", 16384);
        props.put("linger.ms", 1);
        props.put("buffer.memory", 33554432);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        return new KafkaProducer<String, String>(props);
    }

    @Bean
    public HttpComponentsClientHttpRequestFactory customHttpRequestFactory() {
        HttpComponentsClientHttpRequestFactory httpRequestFactory = new HttpComponentsClientHttpRequestFactory();
        return httpRequestFactory;
    }

    @Bean
    public RestTemplate customRestTemplate() {
        return new RestTemplate(customHttpRequestFactory());
    }
}
