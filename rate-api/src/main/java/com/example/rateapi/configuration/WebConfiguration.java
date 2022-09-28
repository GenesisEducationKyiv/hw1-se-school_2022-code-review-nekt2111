package com.example.rateapi.configuration;

import com.example.rateapi.configuration.templates.CoinBaseRestTemplate;
import com.example.rateapi.configuration.templates.CoinGeckoRestTemplate;
import com.example.rateapi.configuration.templates.KucoinRestTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class WebConfiguration {

    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    CoinBaseRestTemplate coinBaseRestTemplate() {
        return new CoinBaseRestTemplate();
    }

    @Bean
    CoinGeckoRestTemplate coinGeckoRestTemplate() {
        return new CoinGeckoRestTemplate();
    }

    @Bean
    KucoinRestTemplate kucoinRestTemplate() {
        return new KucoinRestTemplate();
    }

}
