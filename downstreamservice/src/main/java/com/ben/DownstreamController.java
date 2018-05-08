package com.ben;

import com.ben.handler.ScenarioHandlerFactory;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.ajbrown.namemachine.NameGenerator;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;

@RestController
@Slf4j
@Setter
@RequestMapping("/")
public class DownstreamController {
    @Inject
    private ScenarioHandlerFactory handlerFactory;

    @Inject
    private NameGenerator nameGenerator;

    /**
     * Persons resource (people? :))
     *
     * Just return a {@link Person} containing the name and ID the client passes us.
     * @param id ID to return in the response
     * @return Person containing name and ID passed in request
     */
    @RequestMapping(value = "persons/{id}", method = RequestMethod.GET)
    public Person getPerson(@PathVariable("id") final String id) {
        return handlerFactory.getHandler(id).handle(Person.builder()
                .id(id)
                .name(nameGenerator.generateName().getFirstName())
                .build());
    }
}
