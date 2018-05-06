package com.ben.handler;

import com.ben.Person;

/**
 * Scenario handler
 */
public interface ScenarioHandler<K> {
    /**
     * Handle the request
     * @param request Person
     * @return response Person
     */
    Person handle(Person request);

    /**
     * Get the key that should be used to lookup this handler.
     *
     * For example, if the scenario should be triggered by {@link Person#getId()} = "timeout",
     * this method should return the String "timeout"
     * @return key
     */
    K getKey();
}
