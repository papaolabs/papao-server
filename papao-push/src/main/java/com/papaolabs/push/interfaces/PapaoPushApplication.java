package com.papaolabs.push.interfaces;

import com.papaolabs.client.PushClient;
import com.papaolabs.push.application.ApplicationConfig;
import com.papaolabs.push.domain.DomainConfig;
import com.papaolabs.push.infrastructure.InfrastructureConfig;
import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.Resource;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.io.IOException;

@SpringBootApplication
@EnableEncryptableProperties
@EnableSwagger2
@Import({
            ApplicationConfig.class,
            DomainConfig.class,
            InfrastructureConfig.class
        })
public class PapaoPushApplication {
    @Value("file:${apns.cert.path}")
    private Resource cert;
    @Value("${apns.client.password}")
    private String password;
    @Value("${apns.client.topic}")
    private String topic;

    public static void main(String[] args) {
        SpringApplication.run(PapaoPushApplication.class, args);
    }

    @Bean
    public PushClient pushClient() throws IOException {
        System.out.println(cert.getURL());
        return new PushClient(cert, password, topic);
    }
}
