package com.papaolabs.image.domain;

import com.papaolabs.image.domain.service.StorageConfig;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan
@EnableAutoConfiguration
@Import({
            StorageConfig.class
        })
public class DomainConfig {
}
