package com.example.bitcoingenesis.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.GenericWebApplicationContext;

@Configuration
public class WebConfiguration {

    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
