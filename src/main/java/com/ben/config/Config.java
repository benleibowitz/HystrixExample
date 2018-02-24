package com.ben.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
public class Config {
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
