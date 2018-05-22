package com.dhxz.actuator.config;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ActuatorConfig {

    @Bean
    public HealthIndicator healthIndicator() {
        return ()-> Health.up().withDetail("hello","world").build();
    }
}
