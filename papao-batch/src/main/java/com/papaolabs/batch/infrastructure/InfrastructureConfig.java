package com.papaolabs.batch.infrastructure;

import com.papaolabs.batch.infrastructure.feign.ClientConfig;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@EnableAutoConfiguration
@Import({
            ClientConfig.class
        })
public class InfrastructureConfig {
}
