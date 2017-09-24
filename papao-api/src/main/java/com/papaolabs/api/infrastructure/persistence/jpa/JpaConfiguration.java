package com.papaolabs.api.infrastructure.persistence.jpa;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@ComponentScan
@EnableJpaAuditing
@EnableAutoConfiguration
public class JpaConfiguration {
}
