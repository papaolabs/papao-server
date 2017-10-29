package com.papaolabs.batch.interfaces;

import com.papaolabs.batch.application.ApplicationConfig;
import com.papaolabs.batch.domain.DomainConfig;
import com.papaolabs.batch.infrastructure.InfrastructureConfig;
import com.papaolabs.batch.infrastructure.feign.ClientConfig;
import com.papaolabs.scheduler.SchedulerConfig;
import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@EnableEncryptableProperties
@Import({
            ApplicationConfig.class,
            DomainConfig.class,
            InfrastructureConfig.class,
            SchedulerConfig.class
        })
public class PapaoBatchApplication {
    public static void main(String[] args) {
        SpringApplication.run(PapaoBatchApplication.class, args);
    }
}
