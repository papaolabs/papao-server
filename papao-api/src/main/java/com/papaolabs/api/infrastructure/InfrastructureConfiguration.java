package com.papaolabs.api.infrastructure;

import com.papaolabs.api.infrastructure.persistence.jpa.JpaConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@EnableAutoConfiguration
@Import({
            JpaConfiguration.class
        })
public class InfrastructureConfiguration {
}
