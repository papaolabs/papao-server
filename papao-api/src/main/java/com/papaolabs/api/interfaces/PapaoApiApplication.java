package com.papaolabs.api.interfaces;

import com.papaolabs.api.domain.DomainConfiguration;
import com.papaolabs.api.infrastructure.InfrastructureConfiguration;
import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@SpringBootApplication
@EnableEncryptableProperties
@Import({
		DomainConfiguration.class,
		InfrastructureConfiguration.class
})
public class PapaoApiApplication extends WebMvcConfigurerAdapter {

	public static void main(String[] args) {
		SpringApplication.run(PapaoApiApplication.class, args);
	}
}
