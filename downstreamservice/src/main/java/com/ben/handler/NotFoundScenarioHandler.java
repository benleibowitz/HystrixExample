package com.ben.handler;

import com.ben.Person;
import com.ben.exception.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;

@Slf4j
@Component
public class NotFoundScenarioHandler implements ScenarioHandler<String> {
    @Override
    public Person handle(Person request) {
        log.info("Client has requested a not found scenario");
        throw new NotFoundException("Person with ID: " + request.getId() + " not found");
    }

    @Override
    public Collection<String> getKeys() {
        return Collections.singletonList("notfound");
    }
}
