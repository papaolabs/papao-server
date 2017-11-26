package com.papaolabs.openapi.interfaces;

import com.papaolabs.client.ClientConfig;
import com.papaolabs.openapi.application.ApplicationConfig;
import com.papaolabs.openapi.domain.DomainConfig;
import com.papaolabs.openapi.infrastructure.InfrastructureConfig;
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
        ClientConfig.class
})
public class PapaoOpenApi {

    public static void main(String[] args) {
        SpringApplication.run(PapaoOpenApi.class, args);
    }
}
