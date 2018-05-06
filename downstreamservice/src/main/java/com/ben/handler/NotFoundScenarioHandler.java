package com.ben.handler;

import com.ben.exception.NotFoundException;
import com.ben.Person;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class NotFoundScenarioHandler implements ScenarioHandler<String> {
    @Override
    public Person handle(Person request) {
        log.info("Client has requested a not found scenario");
        throw new NotFoundException("Person with ID: " + request.getId() + " not found");
    }

    @Override
    public String getKey() {
        return "notfound";
    }
}
