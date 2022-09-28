package com.example.notificationapi.client;

import com.example.notificationapi.model.rate.Crypto;
import com.example.notificationapi.model.rate.Currency;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class CryptoRateClientImpl implements CryptoRateClient {

    private final String rateApiUrl;

    private final RestTemplate restTemplate;

    private static final Logger LOGGER = LoggerFactory.getLogger(CryptoRateClient.class);

    public CryptoRateClientImpl(@Value("${bitcoin.genesis.rate.api.url}") String rateApiUrl,
                                RestTemplate restTemplate) {
        this.rateApiUrl = rateApiUrl;
        this.restTemplate = restTemplate;
    }

    @Override
    public Integer getRate() {
        String uri = rateApiUrl + "/rate";
        LOGGER.info("Making request on {}",uri);
        ResponseEntity<Integer> response = restTemplate.getForEntity(rateApiUrl + "/rate", Integer.class);
        LOGGER.info("Received response - {}", response);
        return response.getBody();
    }

    @Override
    public Integer getRateForCryptoInCurrency(Crypto crypto, Currency currency) {

        String uri = UriComponentsBuilder.fromHttpUrl(rateApiUrl + "/rate")
                .pathSegment( crypto.getFullName())
                .queryParam("currency", currency.toString())
                .toUriString();

        LOGGER.info("Making request on {} to get rate for {} in {}",uri, crypto.getFullName(), currency);

        ResponseEntity<Integer> response = restTemplate.getForEntity(uri, Integer.class);

        LOGGER.info("Received response - {}", response);

        return response.getBody();
    }
}
