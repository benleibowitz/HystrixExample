package com.ben.handler;

import com.ben.Person;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Handle the happy path. There should only be one subclass of {@link DefaultScenarioHandler}.
 */
@Slf4j
@Component
public class HappyPathScenarioHandler extends DefaultScenarioHandler<String> {
    /**
     * Return the request as-is
     * @param request incoming
     * @return person
     */
    @Override
    public Person handle(Person request) {
        log.info("Running happy path scenario for Person: " + request);
        return request;
    }

    /**
     * Cannot be called as there is no "key" for the {@link DefaultScenarioHandler}
     * @return throws {@code UnsupportedOperationException}
     */
    @Override
    public String getKey() {
        throw new UnsupportedOperationException("getKey() cannot be called on default scenario handler. It has no" +
                " key - keys are only used to request a specific scenario.");
    }
}
