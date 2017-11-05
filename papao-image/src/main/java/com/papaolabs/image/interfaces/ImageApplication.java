package com.papaolabs.image.interfaces;

import com.papaolabs.image.domain.DomainConfig;
import com.papaolabs.image.infrastructure.InfrastructureConfig;
import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableEncryptableProperties
@EnableSwagger2
@Import({
            DomainConfig.class,
            InfrastructureConfig.class
        })
public class ImageApplication {
    public static void main(String[] args) {
        SpringApplication.run(ImageApplication.class, args);
    }
}
