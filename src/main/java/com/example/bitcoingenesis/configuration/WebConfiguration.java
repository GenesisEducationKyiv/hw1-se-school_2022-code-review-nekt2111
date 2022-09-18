package com.example.bitcoingenesis.configuration;

import com.example.bitcoingenesis.configuration.templates.CoinBaseRestTemplate;
import com.example.bitcoingenesis.configuration.templates.CoinGeckoRestTemplate;
import com.example.bitcoingenesis.configuration.templates.KucoinRestTemplate;
import com.example.bitcoingenesis.model.CryptoPriceInfo;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class WebConfiguration {

    @Bean
    RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate;
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
