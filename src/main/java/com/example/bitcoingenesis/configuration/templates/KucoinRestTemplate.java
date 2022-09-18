package com.example.bitcoingenesis.configuration.templates;

import com.example.bitcoingenesis.model.CryptoPriceInfo;
import com.example.bitcoingenesis.service.rate.providers.kucoin.KucoinFiatPriceDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.List;

public class KucoinRestTemplate extends RestTemplate {

    public KucoinRestTemplate() {
        this.setMessageConverters(httpMessageConverters());
    }


    private static List<HttpMessageConverter<?>> httpMessageConverters() {
        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addDeserializer(CryptoPriceInfo.class, new KucoinFiatPriceDeserializer());

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(simpleModule);

        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setObjectMapper(objectMapper);
        return List.of(converter);
    }

}
