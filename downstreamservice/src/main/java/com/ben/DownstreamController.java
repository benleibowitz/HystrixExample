package com.ben;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@RestController
@Slf4j
@RequestMapping("/")
public class DownstreamController {
    /**
     * Persons resource (people? :))
     *
     * Just return a {@link Person} containing the name and ID the client passes us.
     * @param id ID to return in the response
     * @param name name to return in the response
     * @return Person containing name and ID passed in request
     */
    @RequestMapping(value = "persons/{id}/name/{name}", method = RequestMethod.GET)
    public Person getPerson(@PathVariable("id") final String id,
                            @PathVariable("name") final String name,
                            @RequestParam(value = "should_timeout", required = false) final boolean shouldTimeout) {
        if (shouldTimeout) {
            log.info("Client has request that we timeout. Sleeping 5 seconds");
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        Person person = Person.builder()
                .id(id)
                .name(name)
                .build();
        log.info("Returning person: " + person);
        return person;
    }
}
