package com.papaolabs.api;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableEncryptableProperties
public class PapaoApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(PapaoApiApplication.class, args);
	}
}
