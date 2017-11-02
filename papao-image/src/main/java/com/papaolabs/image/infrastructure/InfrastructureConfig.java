package com.papaolabs.image.infrastructure;

import com.papaolabs.image.infrastructure.feign.vision.VisionApiConfig;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan
@EnableAutoConfiguration
@Import({
            VisionApiConfig.class
        })
public class InfrastructureConfig {
}
