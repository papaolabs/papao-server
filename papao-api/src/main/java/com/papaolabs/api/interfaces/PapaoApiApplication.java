package com.papaolabs.api.interfaces;

import com.papaolabs.api.application.ApplicationConfig;
import com.papaolabs.api.domain.DomainConfiguration;
import com.papaolabs.api.infrastructure.InfrastructureConfiguration;
import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableEncryptableProperties
@EnableSwagger2
@Import({
            ApplicationConfig.class,
            DomainConfiguration.class,
            InfrastructureConfiguration.class
        })
public class PapaoApiApplication extends WebMvcConfigurerAdapter {
    public static void main(String[] args) {
        SpringApplication.run(PapaoApiApplication.class, args);
    }

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2).select()
                                                      .apis(RequestHandlerSelectors.any())
                                                      .paths(PathSelectors.ant("/api/**"))
                                                      .build();
    }
}
