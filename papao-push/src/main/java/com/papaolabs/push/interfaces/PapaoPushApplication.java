package com.papaolabs.push.interfaces;

import com.papaolabs.client.PushClient;
import com.papaolabs.push.application.ApplicationConfig;
import com.papaolabs.push.domain.DomainConfig;
import com.papaolabs.push.infrastructure.InfrastructureConfig;
import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@EnableEncryptableProperties
@Import({
            ApplicationConfig.class,
            DomainConfig.class,
            InfrastructureConfig.class
        })
public class PapaoPushApplication {
    public static void main(String[] args) {
        SpringApplication.run(PapaoPushApplication.class, args);
    }

    @Bean
    public PushClient pushClient() {
        return new PushClient();
    }
}
