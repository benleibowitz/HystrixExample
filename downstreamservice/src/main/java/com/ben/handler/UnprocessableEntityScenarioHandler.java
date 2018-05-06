package com.ben.handler;

import com.ben.Person;
import com.ben.exception.UnprocessableEntityException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class UnprocessableEntityScenarioHandler implements ScenarioHandler<String> {
    @Override
    public Person handle(Person request) {
        log.info("Client has requested an unprocessable entity scenario");
        throw new UnprocessableEntityException();
    }

    @Override
    public String getKey() {
        return "unprocessable";
    }
}
