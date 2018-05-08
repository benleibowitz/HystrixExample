package com.ben.handler;

import com.ben.Person;

import java.util.Collection;

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
     * Get the collection of keys that should be used to lookup this handler.
     *
     * For example, if the scenario should be triggered by {@link Person#getId()} = "timeout",
     * this method should return the String "timeout"
     * @return keys
     */
    Collection<K> getKeys();
}
