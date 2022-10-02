package com.example.rateapi.configuration.templates;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.List;

public class CoinBaseRestTemplate extends RestTemplate {

    public CoinBaseRestTemplate() {
        this.setMessageConverters(httpMessageConverters());
    }

    private static List<HttpMessageConverter<?>> httpMessageConverters() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.UNWRAP_ROOT_VALUE, true);
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setObjectMapper(objectMapper);
        return List.of(converter);
    }
}
