package com.papaolabs.api.infrastructure;

import com.papaolabs.api.infrastructure.persistence.jpa.JpaConfiguration;
import com.papaolabs.api.infrastructure.persistence.restapi.account.AccountKitApiConfig;
import com.papaolabs.api.infrastructure.persistence.restapi.seoul.AnimalApiConfig;
import com.papaolabs.api.infrastructure.persistence.restapi.vision.VisionApiConfig;
import com.papaolabs.api.application.scheduler.SchedulerConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@EnableAutoConfiguration
@Import({
            JpaConfiguration.class,
            AnimalApiConfig.class,
            VisionApiConfig.class,
            AccountKitApiConfig.class,
            SchedulerConfiguration.class
        })
public class InfrastructureConfiguration {
}
