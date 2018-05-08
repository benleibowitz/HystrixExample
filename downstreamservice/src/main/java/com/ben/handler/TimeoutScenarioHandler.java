package com.ben.handler;

import com.ben.Person;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class TimeoutScenarioHandler implements ScenarioHandler<String> {
    @Override
    public Person handle(Person request) {
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            log.warn("Interrupted while sleeping", e);
        }
        throw new IllegalStateException("Should have timed out");
    }

    @Override
    public Collection<String> getKeys() {
        return new ArrayList<String>() {{
            add("timeout");
            add("timeoutfallback");
        }};
    }
}
