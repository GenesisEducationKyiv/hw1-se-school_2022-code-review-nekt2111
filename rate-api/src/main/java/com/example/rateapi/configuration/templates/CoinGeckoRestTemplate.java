package com.example.rateapi.configuration.templates;

import com.example.rateapi.model.CryptoPriceInfo;
import com.example.rateapi.service.providers.coingecko.CoinGeckoSimplePriceDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.List;

public class CoinGeckoRestTemplate extends RestTemplate {

    public CoinGeckoRestTemplate() {
        this.setMessageConverters(httpMessageConverters());
    }

    private static List<HttpMessageConverter<?>> httpMessageConverters() {
        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addDeserializer(CryptoPriceInfo.class, new CoinGeckoSimplePriceDeserializer());

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(simpleModule);

        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setObjectMapper(objectMapper);
        return List.of(converter);
    }
}
