package com.ben;

import com.ben.handler.ScenarioHandlerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {
    @Bean
    public ScenarioHandlerFactory handlerFactory() {
        return new ScenarioHandlerFactory();
    }
}
