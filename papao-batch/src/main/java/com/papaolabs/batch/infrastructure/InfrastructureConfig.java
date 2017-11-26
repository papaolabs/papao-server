package com.papaolabs.batch.infrastructure;

import com.papaolabs.batch.infrastructure.feign.ClientConfig;
import com.papaolabs.batch.infrastructure.jpa.JpaConfig;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@EnableAutoConfiguration
@Import({
            ClientConfig.class,
            JpaConfig.class
        })
public class InfrastructureConfig {
}
