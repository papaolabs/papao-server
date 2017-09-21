package com.papaolabs.api.infrastructure;

import com.papaolabs.api.infrastructure.persistence.restapi.AnimalApiConfig;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@EnableAutoConfiguration
@Import({AnimalApiConfig.class})
public class InfrastructureConfiguration {
}
