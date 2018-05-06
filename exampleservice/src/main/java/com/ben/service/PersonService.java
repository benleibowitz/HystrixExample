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
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Optional;
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
     * If call fails, Hystrix will fallback to {@link #getFromCache(String)}.
     *
     * @param id to fetch from downstream
     * @return Person or fallback
     */
    @HystrixCommand(fallbackMethod = "getFromCache", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "500")
    }, ignoreExceptions = {HttpClientErrorException.class})
    public Person get(final String id) throws DownstreamServiceException {
        log.info("Trying to get object from service");

        try {
            Person person = fetchFromDownstream(id);
            memcachedClient.set(person.getId(), 1000, person);
            return person;
        } catch (HttpClientErrorException e) {
            log.error("Caught HttpClientErrorException", e);
            if (e.getRawStatusCode() < 500) {
                throw e;
            } else {
                throw new DownstreamServiceException(e);
            }
        } catch (IOException e) {
            log.error("Error fetching person from downstream", e);
            throw new DownstreamServiceException(e);
        }
    }

    /**
     * Fetch Person from the downstream service
     * @param id of the person to fetch
     * @return Person
     * @throws IOException if error calling service
     */
    private Person fetchFromDownstream(String id) throws IOException {
        String url = buildDownstreamUri(id);
        String response = restTemplate.getForObject(url, String.class);
        log.debug("Response from downstream: " + response);
        return MAPPER.readValue(response, Person.class);
    }

    /**
     * Fallback method
     * @param id ID to get from cache
     * @return {@link Person} from cache
     */
    public Person getFromCache(final String id) {
        log.warn("Falling back");
        return Optional.ofNullable((Person) memcachedClient.get(id))
                .orElseThrow(() -> new IllegalStateException(String.format("Unable to find person: %s in cache", id)));
    }

    /**
     * Build downstreamservice URI
     *
     * @param id of the Person to fetch
     * @return URI
     */
    private String buildDownstreamUri(String id) {
        return String.format("http://%s:%d/persons/%s/name/%s",
                urlConfig.getHost(),
                urlConfig.getPort(),
                id,
                "ben");
    }
}
