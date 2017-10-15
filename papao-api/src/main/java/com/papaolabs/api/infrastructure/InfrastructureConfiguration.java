package com.papaolabs.api.infrastructure;

import com.papaolabs.api.infrastructure.persistence.jpa.JpaConfiguration;
import com.papaolabs.api.infrastructure.persistence.restapi.AnimalApiConfig;
import com.papaolabs.api.infrastructure.persistence.scheduler.SchedulerConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@EnableAutoConfiguration
@Import({
            JpaConfiguration.class,
            AnimalApiConfig.class,
            SchedulerConfiguration.class
        })
public class InfrastructureConfiguration {
}
