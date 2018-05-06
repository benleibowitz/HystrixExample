package com.ben.handler;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
public class ScenarioHandlerFactory {
    /**
     * Handler lookup map by key
     */
    private static final Map<Object, ScenarioHandler> HANDLER_MAP = new HashMap<>();

    /**
     * All handlers
     */
    @Setter
    @Inject
    List<ScenarioHandler> handlers;

    /**
     * Default scenario handler
     */
    @Setter
    @Inject
    DefaultScenarioHandler defaultHandler;

    /**
     * Build {@link #HANDLER_MAP}
     */
    @PostConstruct
    public void setup() {
        handlers.stream()
                .filter(it -> !(it instanceof DefaultScenarioHandler))
                .forEach(it -> HANDLER_MAP.put(it.getKey(), it));
    }

    /**
     * Get handler for this ID
     * @param id ID
     * @return {@link ScenarioHandler}
     */
    public ScenarioHandler getHandler(String id) {
        return Optional.ofNullable(HANDLER_MAP.get(id)).orElse(defaultHandler);
    }
}
