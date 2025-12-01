package com.will.todo_backend.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneOffset;

// for integration tests...
// use by annotating class with @Import(FixedClockConfig.class)
@TestConfiguration
public class FixedClockConfig {

    @Bean
    public Clock clock() {
        return Clock.fixed(
                Instant.parse("2024-01-15T10:00:00Z"),
                ZoneOffset.UTC
        );
    }
}

