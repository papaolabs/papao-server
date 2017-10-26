package com.papaolabs.openapi.interfaces;

import com.papaolabs.openapi.application.ApplicationConfig;
import com.papaolabs.openapi.domain.DomainConfig;
import com.papaolabs.openapi.infrastructure.InfrastructureConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({
        ApplicationConfig.class,
        DomainConfig.class,
        InfrastructureConfig.class
})
public class PapaoOpenApi {

    public static void main(String[] args) {
        SpringApplication.run(PapaoOpenApi.class, args);
    }
}
