package com.papaolabs.image;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@EnableEncryptableProperties
@Import({
            AwsConfig.class
        })
public class ImageApplication {
    public static void main(String[] args) {
        SpringApplication.run(ImageApplication.class, args);
    }
}
