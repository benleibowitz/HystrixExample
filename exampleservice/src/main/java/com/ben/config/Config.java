package com.ben.config;

import com.ben.Person;
import com.ben.PersonServiceURLConfig;
import net.spy.memcached.MemcachedClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.InetSocketAddress;

@Configuration
public class Config {
    @Bean
    public PersonServiceURLConfig urlConfig(@Value("${sidecarproxy.host}") final String host,
                                            @Value("${sidecarproxy.port}") final int port) {
        return PersonServiceURLConfig.builder()
                .host(host)
                .port(port)
                .build();
    }

    @Bean
    public MemcachedClient memcached(@Value("${memcached.host}") final String host, @Value("${memcached.port}") final int port) throws IOException {
        MemcachedClient client = new MemcachedClient(new InetSocketAddress(host, port));
        client.set("timeoutfallback", 10000, Person.builder()
                .id("timeoutfallback")
                .name("Cacheman McCacheburger")
                .build());
        return client;
    }

    @Bean
    public HttpComponentsClientHttpRequestFactory customHttpRequestFactory() {
        return new HttpComponentsClientHttpRequestFactory();
    }

    @Bean
    public RestTemplate customRestTemplate() {
        return new RestTemplate(customHttpRequestFactory());
    }
}
