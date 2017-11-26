package com.papaolabs.api.infrastructure.persistence.jpa;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@ComponentScan
@EnableJpaAuditing
@EnableAutoConfiguration
@EnableTransactionManagement
public class JpaConfiguration {
}
