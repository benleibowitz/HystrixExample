package com.ben.service;

import com.ben.DownstreamServiceException;
import com.ben.Person;
import com.ben.PersonServiceURLConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import lombok.extern.slf4j.Slf4j;
import net.spy.memcached.MemcachedClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.UUID;

@Slf4j
@Service
public class PersonService {
    @Autowired
    private PersonServiceURLConfig urlConfig;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private MemcachedClient memcachedClient;

    private static final ObjectMapper MAPPER = new ObjectMapper();

    /**
     * Get person from downstream service.
     *
     * If call fails, Hystrix will fallback to {@link #getFromCache(String, boolean)}.
     *
     * @param id to fetch from downstream
     * @param shouldTimeout if the downstream service should simulate a timeout
     * @return Person or fallback
     */
    @HystrixCommand(fallbackMethod = "getFromCache", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "500")
    })
    public Person get(final String id, final boolean shouldTimeout) throws DownstreamServiceException {
        log.info("Trying to get object from service");
        String url = buildDownstreamUri(id, shouldTimeout);
        String response = restTemplate.getForObject(url, String.class);

        try {
            return MAPPER.readValue(response, Person.class);
        } catch (IOException e) {
            log.error("Error reading JSON: " + response, e);
            throw new DownstreamServiceException(e);
        }
    }

    /**
     * Fallback method
     * @param id ID to get from cache
     * @param shouldTimeout not used
     * @return {@link Person} from cache
     */
    public Person getFromCache(final String id, final boolean shouldTimeout) {
        log.warn("Falling back");
        return (Person) memcachedClient.get(id);
    }

    /**
     * Build downstreamservice URI
     *
     * @param id of the Person to fetch
     * @param shouldTimeout whether the downstream service should timeout
     * @return
     */
    private String buildDownstreamUri(String id, boolean shouldTimeout) {
        String url = String.format("http://%s:%d/persons/%s/name/%s",
                urlConfig.getHost(),
                urlConfig.getPort(),
                id,
                "ben");
        if (shouldTimeout) {
            url += "?should_timeout=true";
        }
        return url;
    }
}
