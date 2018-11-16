package com.invision.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class InVisionApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(InVisionApiApplication.class, args);
    }

    @Bean
    public RestTemplate provideRestTemplate() {
        return new RestTemplate();
    }
}
