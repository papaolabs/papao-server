package com.papaolabs.push.infrastructure;

import com.papaolabs.push.infrastructure.persistence.jpa.JpaConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan
@Import({
            JpaConfiguration.class
        })
public class InfrastructureConfig {
}
