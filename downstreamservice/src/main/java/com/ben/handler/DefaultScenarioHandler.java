package com.ben.handler;

/**
 * Default scenario handler. This handler will be used if no other matching key
 * is found for requesting a specific scenario. There should only be one subclass
 * so the controller knows which is the default.
 */
public abstract class DefaultScenarioHandler<K> implements ScenarioHandler<K> {
}
