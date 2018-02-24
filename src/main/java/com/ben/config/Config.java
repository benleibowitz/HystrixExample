package com.ben.config;

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
        Properties prop = new Properties();

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
